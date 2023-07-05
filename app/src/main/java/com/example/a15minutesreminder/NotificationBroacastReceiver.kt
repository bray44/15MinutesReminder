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

    override fun onReceive(context: Context, p1: Intent?) {
        val CHANNEL_ID = "1"
        val NOTIFICATION_ID = 1

        Log.d("ALARM", "onReceive called")

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        val notification = Notification.Builder(context, CHANNEL_ID)
            .setContentTitle("Awesome notification")
            .setContentText("This is contentn text")
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManagerCompat.notify(NOTIFICATION_ID, notification)

    }

}