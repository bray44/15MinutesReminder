package com.example.a15minutesreminder

import android.util.Log
import java.util.Calendar

class Time {

    companion object {

        fun getNearest15MinutesinMillis(): Long {
            val currentTime = System.currentTimeMillis()
            val timeInterval = 15 * 60 * 1000
            val modulus = currentTime % timeInterval
            val milliSecondsNeeded = timeInterval - modulus

            return currentTime + milliSecondsNeeded
        }

        fun getNearest15Minutes(): String {

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = getNearest15MinutesinMillis()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val mHour = hour.toString().padStart(2, '0')
            val mMinute = minute.toString().padStart(2, '0')

            return "$mHour:$mMinute"
        }

        fun timeNowIsInsideAlarmWindow(alarmSettingsState: AlarmSettings): Boolean {


            val startTime = convertToMinute(alarmSettingsState.startTimeAtUi)

            var stopTime = convertToMinute(alarmSettingsState.stopTimeAtUi)
            if (stopTime < startTime) { stopTime += (24 * 60) }

            val currentTime = convertToMinute(getNearest15Minutes())

            return currentTime in startTime..stopTime

        }

        fun convertToMinute(uiTime: String): Int {

            var hour = uiTime.substring(0, 2).toInt()
            if ( hour == 0) { hour = 24 }

            val minute = uiTime.substring(3).toInt()

            return (hour * 60) + minute
        }


        fun convertToMillis(time: String): Long {

            val hour = time.substring(0, 2).toInt()
            val minute = time.substring(3).toInt()

            var timeChosen = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            Log.d("ALARM", "timeChosen is $timeChosen")

            if (timeChosen < System.currentTimeMillis()) {
                timeChosen += (24 * 60 * 60 * 1000)

            }

            return timeChosen

        }
    }
}