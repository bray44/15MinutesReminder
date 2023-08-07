package com.example.a15minutesreminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.a15minutesreminder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: AlarmSettingsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNotification()

        val alarmScheduler = AlarmScheduler(this)

        mViewModel = ViewModelProvider(this)[AlarmSettingsViewModel::class.java]

        var alarmSettingsState = mViewModel.getAlarmSettings()


        mViewModel.getAlarmSettingsLiveData().observe(this) {
            alarmSettingsState = it
            alarmSettingsState.alarmStatus = it.alarmStatus
            binding.tvPointsLeft.text = "You have ${it.mutableIntervalPoints} points left"
            binding.tvCountDown.text = setNearestTriggerTime(it.mutableIntervalPoints)
            binding.btnClickMe.text = setButtonState(it.alarmStatus)
        }

        binding.btnClickMe.setOnClickListener {

            if (alarmSettingsState.alarmStatus) {
                Log.d("ALARM", "ALARM is read as true")
                mViewModel.setAlarmStatusTo(false)
                alarmScheduler.cancelAllAlarm()
            }
            else
            {
                Log.d("ALARM", "ALARM is read as false")


                val triggerMillisStartAlarm = Time.convertToMillis(alarmSettingsState.startTimeAtUi)
                val triggerMillisStopAlarm = Time.convertToMillis(alarmSettingsState.stopTimeAtUi)

                if (Time.timeNowIsInsideAlarmWindow(alarmSettingsState)) {

                    mViewModel.setMutableIntervalPoints(alarmSettingsState, Time.getNearest15Minutes(), alarmSettingsState.stopTimeAtUi)
                    alarmScheduler.setRepeatingAlarmNow(triggerTime = Time.getNearest15MinutesinMillis())

                }

                mViewModel.updateAlarmSettingsToDb(alarmSettingsState)

                mViewModel.setAlarmStatusTo(true)

                alarmScheduler.setStartAlarm(triggerTime = triggerMillisStartAlarm)
                alarmScheduler.setStopAlarm(triggerTime = triggerMillisStopAlarm)
            }
        }

        binding.btnTime.setOnClickListener {
            startActivity(Intent(this, AlarmSettingsActivity::class.java))
        }
    }



    private fun setNotification() {
        val CHANNEL_ID = "1"
        val CHANNEL_NAME = "ALARM"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
    }






    private fun setButtonState(status: Boolean): String {

        return if (status) {
            "Pause"
        } else {
            "Start"
        }

    }



    private fun setNearestTriggerTime(uselessInput: Int): String {

        val alarmSettingsState = mViewModel.getAlarmSettings()

        return if (Time.timeNowIsInsideAlarmWindow(alarmSettingsState)) {
            Time.getNearest15Minutes()
        } else {
            alarmSettingsState.startTimeAtUi
        }

    }

}
