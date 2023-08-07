package com.example.a15minutesreminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StopAlarmBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("ALARM", "StopAlarmBroadcast called")
        val alarmScheduler = AlarmScheduler(context!!)

        alarmScheduler.cancelRepeatingAlarm()

    }
}