package com.example.a15minutesreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.a15minutesreminder.databinding.ActivityAlarmSettingsBinding
import com.google.android.material.timepicker.MaterialTimePicker

class AlarmSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmSettingsBinding
    private lateinit var mViewModel: AlarmSettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this)[AlarmSettingsViewModel::class.java]

        binding.tvStartTime.text = mViewModel.getAlarmSettings().startTimeAtUi
        binding.tvStopTime.text = mViewModel.getAlarmSettings().stopTimeAtUi

        val alarmScheduler = AlarmScheduler(this)

        val startTimePicker = MaterialTimePicker.Builder()
            .setHour(mViewModel.getHour(AlarmSettings.START_TIME))
            .setMinute(mViewModel.getMinutes(AlarmSettings.START_TIME))
            .build()

        val stopTimePicker = MaterialTimePicker.Builder()
            .setHour(mViewModel.getHour(AlarmSettings.STOP_TIME))
            .setMinute(mViewModel.getMinutes(AlarmSettings.STOP_TIME))
            .build()

        startTimePicker.addOnPositiveButtonClickListener {
            binding.tvStartTime.text = setTimeFor(startTimePicker)
        }

        stopTimePicker.addOnPositiveButtonClickListener {
            binding.tvStopTime.text = setTimeFor(stopTimePicker)
        }

        binding.tvStartTime.setOnClickListener {
            startTimePicker.show(supportFragmentManager, "START TIME")
        }

        binding.tvStopTime.setOnClickListener {
            stopTimePicker.show(supportFragmentManager, "STOP TIME")
        }

        binding.btnSaveAlarmSettings.setOnClickListener {

            val alarmSettingsState = mViewModel.getAlarmSettings()
            val newStartTime = binding.tvStartTime.text.toString()
            val newStopTime = binding.tvStopTime.text.toString()

            mViewModel.setStartAndStopTime(alarmSettingsState, newStartTime, newStopTime)
            mViewModel.setIntervalPoints(alarmSettingsState, newStartTime, newStopTime)

            if (Time.timeNowIsInsideAlarmWindow(alarmSettingsState)) {
                mViewModel.setMutableIntervalPoints(alarmSettingsState, Time.getNearest15Minutes(), newStopTime)
            } else {
                mViewModel.setMutableIntervalPoints(alarmSettingsState, newStartTime, newStopTime)
            }

            mViewModel.updateAlarmSettingsToDb(alarmSettingsState)

            val triggerStartAlarm = Time.convertToMillis(newStartTime)
            val triggerStopAlarm = Time.convertToMillis(newStopTime)

            alarmScheduler.setStartAlarm(triggerTime = triggerStartAlarm)
            alarmScheduler.setStopAlarm(triggerTime = triggerStopAlarm)

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }

    private fun setTimeFor(timePicker: MaterialTimePicker): String {
        val hour = timePicker.hour.toString().padStart(2, '0')
        val minute = timePicker.minute.toString().padStart(2, '0')

        return "$hour:$minute"
    }









}

