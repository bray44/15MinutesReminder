package com.example.a15minutesreminder

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.a15minutesreminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    private val NOTIFICATION_ID = 0
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        createNotificationChannel()
        createNotificationChannelTwo()

        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Awesome notification")
            .setContentText("This is contentn text")
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setPriority(Notification.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

    binding.btnClickMe.setOnClickListener {
        NotificationManagerCompat.from(this)
            .notify(NOTIFICATION_ID, notification)
    }



    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun createNotificationChannelTwo() {
        val channel = NotificationChannel("WOW", "WADIDAW", NotificationManager.IMPORTANCE_HIGH )
            .apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

}