package com.example.a15minutesreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


        Log.d("ALARM", "AASASA ${mViewModel.getAlarmSettingsState().startTimeAtUi}")

        binding.tvStartTime.text = mViewModel.getAlarmSettingsState().startTimeAtUi
        binding.tvStopTime.text = mViewModel.getAlarmSettingsState().stopTimeAtUi


        val startTimePicker = MaterialTimePicker.Builder()
            .setHour(mViewModel.getHour(AlarmSettings.START_TIME))
            .setMinute(mViewModel.getMinutes(AlarmSettings.START_TIME))
            .build()

        val stopTimePicker = MaterialTimePicker.Builder()
            .setHour(mViewModel.getHour(AlarmSettings.STOP_TIME))
            .setMinute(mViewModel.getMinutes(AlarmSettings.STOP_TIME))
            .build()


        startTimePicker.addOnPositiveButtonClickListener {
            mViewModel.setTimeMilllis(AlarmSettings.START_TIME, startTimePicker)
            mViewModel.setTimeAtUi(AlarmSettings.START_TIME, startTimePicker)
            binding.tvStartTime.text = mViewModel.getAlarmSettingsState().startTimeAtUi
        }

        stopTimePicker.addOnPositiveButtonClickListener {
            mViewModel.setTimeMilllis(AlarmSettings.STOP_TIME, stopTimePicker)
            mViewModel.setTimeAtUi(AlarmSettings.STOP_TIME, stopTimePicker)
            binding.tvStopTime.text = mViewModel.getAlarmSettingsState().stopTimeAtUi
        }

        binding.tvStartTime.setOnClickListener {
            startTimePicker.show(supportFragmentManager, "START TIME")
        }

        binding.tvStopTime.setOnClickListener {
            stopTimePicker.show(supportFragmentManager, "STOP TIME")
        }

        binding.btnSaveAlarmSettings.setOnClickListener {

            val alarmSettingsState = mViewModel.getAlarmSettingsState()
            val startTimeAdapted = mViewModel.adaptTimeMillis(alarmSettingsState.startTime)
            val stopTimeAdapted = mViewModel.adaptTimeMillis(alarmSettingsState.stopTime)
            Log.d("ALARM", "the start is $startTimeAdapted & the stop is $stopTimeAdapted")

            mViewModel.setIntervalPoints(startTimeAdapted, stopTimeAdapted)
            mViewModel.updateAlarmSettingsToDb(alarmSettingsState)

            startActivity(Intent(this, MainActivity::class.java))/// somehow I need to make new instace of main so, the viewmodel is updated
            finish()

        }
    }



}

