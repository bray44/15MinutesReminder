package com.example.a15minutesreminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmSettingsViewModel(application: Application): AndroidViewModel(application) {


    private val db by lazy {
        Room.databaseBuilder(
            application,
            AlarmSettingsDatabase::class.java,
            "alarm-settings-database"
        ).build().dao()
    }

    private val alarmSettings = AlarmSettings(
        startTime = 0,
        stopTime = 0,
        startTimeAtUi = "00:00",
        stopTimeAtUi = "00:00"
    )




    private val mAlarmSettings = MutableLiveData(alarmSettings)
    private val _mAlarmSettings: LiveData<AlarmSettings> = mAlarmSettings


    fun insertAlarmSettings() {
        viewModelScope.launch (Dispatchers.IO) {
            db.insertAlarmSettings(alarmSettings)
        }
    }


    fun updateAlarmSettings(usage: Int, time: Long) {

        if (usage == AlarmSettings.START_TIME) {
                alarmSettings.startTime = time
                mAlarmSettings.value = alarmSettings
            } else if (usage == AlarmSettings.STOP_TIME)  {
                alarmSettings.stopTime = time
                mAlarmSettings.value = alarmSettings
            }

        viewModelScope.launch(Dispatchers.IO) {
            db.updateAlarmSettings(alarmSettings)
        }

    }



    fun updateTimeAtUi(type: Int, hour: Int, minutes:Int) {


        val mHour = hour.toString().padStart(2, '0') // change number frmt from 0 to 00

        val mMinute = minutes.toString().padStart(2, '0') // change number frmt from 0 to 00

        if (type == AlarmSettings.START_TIME) {
            alarmSettings.startTimeAtUi = "$mHour:$mMinute"
            mAlarmSettings.value = alarmSettings
        } else {
            alarmSettings.stopTimeAtUi = "$mHour:$mMinute"
            mAlarmSettings.value = alarmSettings
        }

        viewModelScope.launch(Dispatchers.IO) {
            db.updateAlarmSettings(alarmSettings)
        }
    }

    fun getAlarmSettingsLiveData(): LiveData<AlarmSettings> {

        viewModelScope.launch(Dispatchers.IO) {
            db.updateAlarmSettings(alarmSettings)
        }
        return db.getAlarmSettings()
    }







}