package com.example.a15minutesreminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.a15minutesreminder.databinding.ActivityAlarmSettingsBinding
import java.util.Calendar

class AlarmSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmSettingsBinding
    private lateinit var mViewModel: AlarmSettingsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this)[AlarmSettingsViewModel::class.java]

        var startTime: Long = 0
        var stopTime: Long = 0

        mViewModel.getAlarmSettingsLiveData().observe(this) {
            binding.tvStartTime.text = it.startTimeAtUi
            binding.tvStopTime.text = it.stopTimeAtUi
            startTime = it.startTime
            stopTime = it.stopTime
            }




        val startAlarmIntent = Intent(this, StartAlarmBroadcastReceiver::class.java)
        val stopAlarmIntent = Intent(this, StopAlarmBroadcastReceiver::class.java)

        val startAlarmPendingIntent =
            PendingIntent.getBroadcast(this, 0, startAlarmIntent, PendingIntent.FLAG_MUTABLE)

        val stopAlarmPendingIntent =
            PendingIntent.getBroadcast(this, 0, stopAlarmIntent, PendingIntent.FLAG_MUTABLE)

        val startAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager





        binding.tvStartTime.setOnClickListener {
            StartTimePickerFragment().show(supportFragmentManager, "start_time")
            }

        binding.tvStopTime.setOnClickListener {
                StopTimePickerFragment().show(supportFragmentManager, "stop_time")
            }

        binding.btnSaveAlarmSettings.setOnClickListener {
            startAlarmManager.setRepeating(
                    AlarmManager.RTC,
                    startTime,
                    AlarmManager.INTERVAL_DAY,
                    startAlarmPendingIntent
                )
            startAlarmManager.setRepeating(
                    AlarmManager.RTC,
                    stopTime,
                    AlarmManager.INTERVAL_DAY,
                    stopAlarmPendingIntent
                )
                mViewModel.insertAlarmSettings()
            }


        }

    }
