package com.example.a15minutesreminder

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.a15minutesreminder.databinding.ActivityAlarmSettingsBinding

class AlarmSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmSettingsBinding
    private lateinit var mViewModel: AlarmSettingsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this)[AlarmSettingsViewModel::class.java]


        val startAlarmIntent = Intent(this,StartAlarmBroadcastReceiver::class.java )
        val stopAlarmIntent = Intent(this, StopAlarmBroadcastReceiver::class.java)

        val startAlarmPendingIntent =
            PendingIntent.getBroadcast(this, 0, startAlarmIntent, PendingIntent.FLAG_MUTABLE)

        val stopAlarmPendingIntent =
            PendingIntent.getBroadcast(this, 0, stopAlarmIntent, PendingIntent.FLAG_MUTABLE)

        binding.btnStartTime.setOnClickListener {
            StartTimePickerFragment().show(supportFragmentManager, "time")
        }

        binding.button2.setOnClickListener {
            Log.d("ALARM", "The hour is ${mViewModel.getStartTime()[1]} & the minutes is ${mViewModel.getStartTime()[0]}")
        }




    }
}