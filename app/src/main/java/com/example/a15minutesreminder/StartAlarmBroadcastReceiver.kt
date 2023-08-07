package com.example.a15minutesreminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StartAlarmBroadcastReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("ALARM", "StartAlarmBroadcast called")
        val alarmScheduler = AlarmScheduler(context!!)


        fun triggerMillis(interval: Int): Long {
            val currentTime = System.currentTimeMillis()
            val timeInterval = interval * 60 * 1000
            val modulus = currentTime % timeInterval
            val milliSecondsNeeded = timeInterval - modulus
            val triggerMillis = currentTime + milliSecondsNeeded
            Log.d("ALARM", "The trigger millis will be at $triggerMillis")
            return triggerMillis
        }

        val db = AlarmSettingsDatabase.getDatabase(context).dao()
        val alarmSettings = db.getAlarmSettings()

        alarmSettings.mutableIntervalPoints = alarmSettings.intervalPoints
        db.updateAlarmSettings(alarmSettings)

        alarmScheduler.setStartAlarm(triggerTime = triggerMillis(15))






    }

}