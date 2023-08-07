package com.example.a15minutesreminder

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmSettingsViewModel(application: Application): AndroidViewModel(application) {

    private val db by lazy {
        AlarmSettingsDatabase.getDatabase(application).dao()
    }

    private var alarmSettings = getAlarmSettings()

    fun getAlarmSettings(): AlarmSettings {
        return db.getAlarmSettings()
    }

    fun getAlarmSettingsLiveData(): LiveData<AlarmSettings> {
        return db.getAlarmSettingsLiveData()
    }

    fun getHour(alarmType: Int): Int {

        return if(alarmType == AlarmSettings.START_TIME) {
            getAlarmSettings().startTimeAtUi.substring(0, 2).toInt()
        } else {
            getAlarmSettings().stopTimeAtUi.substring(0, 2).toInt()
        }
    }

    fun getMinutes(alarmType: Int): Int {

        return if(alarmType == AlarmSettings.START_TIME) {
            getAlarmSettings().startTimeAtUi.substring(3).toInt()
        } else {
            getAlarmSettings().stopTimeAtUi.substring(3).toInt()
        }
    }

    fun updateAlarmSettingsToDb(alarmSettings: AlarmSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            db.updateAlarmSettings(alarmSettings)
        }
    }

    fun setAlarmStatusTo(newStatus: Boolean) {

        val alarmSettings = getAlarmSettings()

        alarmSettings.alarmStatus = newStatus

        viewModelScope.launch(Dispatchers.IO) {
            db.updateAlarmSettings(alarmSettings)
            Log.d("ALARM", "Alarm status changed to ${alarmSettings.alarmStatus}")
        }


    }

    fun setStartAndStopTime(alarmSettingsState: AlarmSettings, newStartTime: String, newStopTime: String) {

        alarmSettingsState.startTimeAtUi = newStartTime
        alarmSettingsState.stopTimeAtUi = newStopTime

    }

    fun setIntervalPoints(alarmSettings: AlarmSettings, startTime: String, stopTime: String) {

        alarmSettings.intervalPoints = calculateIntervalPoints(startTime, stopTime)
    }

    fun setMutableIntervalPoints(alarmSettings: AlarmSettings, startTime: String, stopTime: String) {

        alarmSettings.mutableIntervalPoints = calculateIntervalPoints(startTime, stopTime)
    }

    private fun calculateIntervalPoints(startTime: String, stopTime: String): Int {

        val startTimeTotal = Time.convertToMinute(startTime)
        val stopTimeTotal = Time.convertToMinute(stopTime)
        var intervalPoints = (stopTimeTotal - startTimeTotal)/15

        if (intervalPoints <= 0) { intervalPoints += 96 }

        return intervalPoints
    }















}