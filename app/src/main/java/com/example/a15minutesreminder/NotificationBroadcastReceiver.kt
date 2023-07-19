package com.example.a15minutesreminder

import android.Manifest
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat

class NotificationBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val CHANNEL_ID = "1"
        val NOTIFICATION_ID = 1

        Log.d("ALARM", "NotificationBroadcast called")

        val db = AlarmSettingsDatabase.getDatabase(context).dao()

        val alarmSettings = db.getAlarmSettings()
        val intervalPoints = alarmSettings.intervalPoints

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        val notification = Notification.Builder(context, CHANNEL_ID)
            .setContentTitle("Awesome notification")
            .setContentText("You have $intervalPoints left")
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
          //do permission
            return
        }
        notificationManagerCompat.notify(NOTIFICATION_ID, notification)

        alarmSettings.intervalPoints = intervalPoints - 1
        db.updateAlarmSettings(alarmSettings)
    }
}