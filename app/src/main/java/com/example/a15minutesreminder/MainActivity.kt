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
import com.example.a15minutesreminder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val CHANNEL_ID = "1"
        val CHANNEL_NAME = "ALARM"
        val NOTIFICATION_ID = 1

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)


        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(this, NotificationBroadcastReceiver::class.java)
        val notificationPendingIntent =
            PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)

        val stopAlarmIntent = Intent(this, StopAlarmBroadcastReceiver::class.java)
        val stopAlarmPendingIntent =
            PendingIntent.getBroadcast(this, 0, stopAlarmIntent, PendingIntent.FLAG_MUTABLE)


        fun triggerMillis(interval: Int): Long {
            val currentTime = System.currentTimeMillis()
            val timeInterval = interval * 60 * 1000
            val modulus = currentTime % timeInterval
            val miliSecondsNeeded = timeInterval - modulus
            val triggerMillis = currentTime + miliSecondsNeeded
            Log.d("ALARM", "The trigger millis will be at $triggerMillis")
            return triggerMillis
        }


        binding.btnClickMe.setOnClickListener {
            Log.d("ALARM", "onClick called")
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerMillis(15), AlarmManager.INTERVAL_FIFTEEN_MINUTES, notificationPendingIntent)
        }

        binding.btnTime.setOnClickListener {
            startActivity(
                Intent(this, AlarmSettingsActivity::class.java)
            )
        }






    }
}