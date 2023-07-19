package com.example.a15minutesreminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StartAlarmBroadcastReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val startAlarmAlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val startIntervalAlarmIntent = Intent(context, NotificationBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, startIntervalAlarmIntent, PendingIntent.FLAG_MUTABLE)

        Log.d("ALARM", "StartAlarmBroadcast called")

        fun triggerMillis(interval: Int): Long {
            val currentTime = System.currentTimeMillis()
            val timeInterval = interval * 60 * 1000
            val modulus = currentTime % timeInterval
            val miliSecondsNeeded = timeInterval - modulus
            val triggerMillis = currentTime + miliSecondsNeeded
            Log.d("ALARM", "The trigger millis will be at $triggerMillis")
            return triggerMillis
        }


        startAlarmAlarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, triggerMillis(15), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent
        )




    }

}