package com.example.a15minutesreminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class AlarmScheduler(context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val notificationPendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, NotificationBroadcastReceiver::class.java),
        PendingIntent.FLAG_MUTABLE
    )

    private val startAlarmPendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, StartAlarmBroadcastReceiver::class.java),
        PendingIntent.FLAG_MUTABLE
    )

    private val stopAlarmPendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, StopAlarmBroadcastReceiver::class.java),
        PendingIntent.FLAG_MUTABLE
    )

    fun setRepeatingAlarmNow(triggerTime: Long) {

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            notificationPendingIntent
        )
    }

    fun setStartAlarm(triggerTime: Long) {

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            AlarmManager.INTERVAL_DAY,
            startAlarmPendingIntent
        )
    }

    fun setStopAlarm(triggerTime: Long) {

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            AlarmManager.INTERVAL_DAY,
            stopAlarmPendingIntent
        )
    }

    fun cancelAllAlarm() {
        alarmManager.cancel(notificationPendingIntent)
        alarmManager.cancel(startAlarmPendingIntent)
        alarmManager.cancel(stopAlarmPendingIntent)
    }

    fun cancelRepeatingAlarm() {
        alarmManager.cancel(notificationPendingIntent)
    }

}