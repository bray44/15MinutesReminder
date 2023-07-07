package com.example.a15minutesreminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar

class AlarmSettingsViewModel(): ViewModel() {



    private val alarmSettings = AlarmSettings(
        startTime = 0,
        stopTime = 0,
        startTimeAtUi = mutableListOf(0,0),
        stopTimeAtUi = mutableListOf(0,0)
    )


    private val mAlarmSettings = MutableLiveData(alarmSettings)
    private val _mAlarmSettings: LiveData<AlarmSettings> = mAlarmSettings


    fun updateAlarmSettings(usage: Int, time: Long) {


            if (usage == AlarmSettings.START_TIME) {
                alarmSettings.startTime = time
                mAlarmSettings.value = alarmSettings
            } else if (usage == AlarmSettings.STOP_TIME)  {
                alarmSettings.stopTime = time
                mAlarmSettings.value = alarmSettings
            }

    }

    fun updateStartTimeAtUi(hour: Int, minutes:Int) {


        alarmSettings.startTimeAtUi = mutableListOf(hour, minutes)
        mAlarmSettings.value = alarmSettings

    }

    fun updateStopTimeAtUi(hour: Int, minutes:Int) {

        alarmSettings.stopTimeAtUi = mutableListOf(hour, minutes)
        mAlarmSettings.value = alarmSettings

    }

    fun getStartTimeAtUi(): LiveData<AlarmSettings> {
        return _mAlarmSettings

    }

    fun getStopTimeAtUi(): LiveData<AlarmSettings> {
        return _mAlarmSettings
    }


    fun getStartTime(): Long {
        return alarmSettings.startTime
    }

    fun getStopTime(): Long {
        return alarmSettings.stopTime
    }




}