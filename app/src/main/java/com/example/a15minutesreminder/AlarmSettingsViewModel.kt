package com.example.a15minutesreminder

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.timepicker.MaterialTimePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class AlarmSettingsViewModel(application: Application): AndroidViewModel(application) {

    private val db by lazy {
        AlarmSettingsDatabase.getDatabase(application).dao()
    }

    private var alarmSettings = db.getAlarmSettings()

    fun updateAlarmSettingsToDb(alarmSettings: AlarmSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            db.updateAlarmSettings(alarmSettings)
        }
    }

    fun getAlarmSettingsState(): AlarmSettings {
        return alarmSettings
    }

    fun setIntervalPoints(startPoints: Long, endPoints: Long){

        var points = ((endPoints - startPoints)/(15 * 60 * 1000)).toInt()

        if (points <= 0) {
            points += 96
        }

        val alarmSettings = getAlarmSettingsState()

        alarmSettings.intervalPoints = points

        viewModelScope.launch(Dispatchers.IO) {
            db.updateAlarmSettings(alarmSettings)
        }

    }



    fun setTimeMilllis(type: Int, timePicker: MaterialTimePicker) {
        val timeChosen = Calendar.getInstance().apply {
            set(Calendar.HOUR, timePicker.hour)
            set(Calendar.MINUTE, timePicker.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis + 43200000 //this long number is 12 hours, bcoz somehow its ketuker between AM and PM


        if (type == AlarmSettings.START_TIME) {
            alarmSettings.startTime = timeChosen
        } else {
            alarmSettings.stopTime = timeChosen
        }

    }

    fun adaptTimeMillis(timeChosen: Long): Long { // to check apakah time nya udh lewat atau belum
        var timeChosen1 = timeChosen
        if (timeChosen1 > System.currentTimeMillis()) {
            timeChosen1 -= 86400000
        }
        return timeChosen1
    }


    fun setTimeAtUi(type: Int, timePicker: MaterialTimePicker) {
        val hour = timePicker.hour.toString().padStart(2, '0')
        val minute = timePicker.minute.toString().padStart(2, '0')

        if (type == AlarmSettings.START_TIME) {
            alarmSettings.startTimeAtUi = "$hour:$minute"
            Log.d("ALARM", "the setTimeAtUi and ${alarmSettings.startTimeAtUi}")
        } else {
            alarmSettings.stopTimeAtUi = "$hour:$minute"
        }


    }

    fun getHour(alarmSettings: Int): Int {

        return if(alarmSettings == AlarmSettings.START_TIME) {
            db.getAlarmSettings().startTimeAtUi.substring(0, 2).toInt()
        } else {
            db.getAlarmSettings().stopTimeAtUi.substring(0, 2).toInt()
        }

    }

    fun getMinutes(alarmSettings: Int): Int {

        return if(alarmSettings == AlarmSettings.START_TIME) {
            db.getAlarmSettings().startTimeAtUi.substring(3).toInt()
        } else {
            db.getAlarmSettings().stopTimeAtUi.substring(3).toInt()
        }

    }

    fun getAlarmSettingsLiveData(): LiveData<AlarmSettings> {
        return db.getAlarmSettingsLiveData()
    }





}