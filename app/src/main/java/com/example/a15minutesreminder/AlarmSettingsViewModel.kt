package com.example.a15minutesreminder

import androidx.lifecycle.ViewModel

class AlarmSettingsViewModel(): ViewModel() {

    private val alarmSettings = AlarmSettings()


    fun setStartTime(startTime: List<Int>) {
        alarmSettings.startTime = startTime
    }

    fun getStartTime(): List<Int> {
        return alarmSettings.startTime
    }


}