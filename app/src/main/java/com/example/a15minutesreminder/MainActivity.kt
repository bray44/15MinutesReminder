package com.example.a15minutesreminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
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

        val CHANNEL_ID = "1"
        val CHANNEL_NAME = "ALARM"

        mViewModel = ViewModelProvider(this)[AlarmSettingsViewModel::class.java]

        val alarmSettingsState = mViewModel.getAlarmSettingsState()

        mViewModel.getAlarmSettingsLiveData().observe(this) {
            alarmSettingsState.startTime = it.startTime
            alarmSettingsState.startTimeAtUi = it.startTimeAtUi
            alarmSettingsState.stopTime = it.stopTime
            alarmSettingsState.stopTimeAtUi = it.stopTimeAtUi

        }



        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(this, NotificationBroadcastReceiver::class.java)

        val notificationPendingIntent =
            PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)

        val startAlarmPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            Intent(this, StartAlarmBroadcastReceiver::class.java),
            PendingIntent.FLAG_MUTABLE)

        val stopAlarmPendingIntent = PendingIntent.getBroadcast(this,
            0,
            Intent(this, StopAlarmBroadcastReceiver::class.java),
            PendingIntent.FLAG_MUTABLE)

        fun findTheNearest15Min(interval: Int): Long {
            val currentTime = System.currentTimeMillis()
            val timeInterval = interval * 60 * 1000
            val modulus = currentTime % timeInterval
            val miliSecondsNeeded = timeInterval - modulus
            val triggerMillis = currentTime + miliSecondsNeeded
            Log.d("ALARM", "The trigger millis will be at $triggerMillis")
            return triggerMillis
        }


        binding.btnClickMe.setOnClickListener {



            val startTimeChecked = mViewModel.adaptTimeMillis(alarmSettingsState.startTime)

            val stopTimeChecked = mViewModel.adaptTimeMillis(alarmSettingsState.stopTime)

            val currentTime = System.currentTimeMillis()

            var firstTriggerToStartAlarm = alarmSettingsState.startTime



            if (currentTime in startTimeChecked..stopTimeChecked) {
                firstTriggerToStartAlarm = findTheNearest15Min(15)
                Log.d("ALARM", "The is called}")
                mViewModel.setIntervalPoints(firstTriggerToStartAlarm, stopTimeChecked)


            } else {
                mViewModel.setIntervalPoints(startTimeChecked, stopTimeChecked)

            }



            // Start repeating alarm based on trigger
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                firstTriggerToStartAlarm,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                notificationPendingIntent
            )

            // Starting repeating alarm automatically
            alarmManager.setRepeating(
                AlarmManager.RTC,
                firstTriggerToStartAlarm,
                AlarmManager.INTERVAL_DAY,
                startAlarmPendingIntent
            )

            // Stopping alarm automatically
            alarmManager.setRepeating(
                AlarmManager.RTC,
                alarmSettingsState.stopTime,
                AlarmManager.INTERVAL_DAY,
                stopAlarmPendingIntent
            )
        }

        binding.btnTime.setOnClickListener {
            startActivity(Intent(this, AlarmSettingsActivity::class.java))
        }
    }





}
