package com.example.a15minutesreminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar

class AlarmSettingsViewModel(): ViewModel() {




    private val alarmSettings =
        AlarmSettings(
            startTime = Calendar.getInstance(),
            stopTime = Calendar.getInstance(),
            startTimeAtUi = "00:00",
            stopTimeAtUi = "00:00"
        )

    val startTimeAtUiObserver = MutableLiveData<String>(alarmSettings.startTimeAtUi)
    val stopTimeAtUiObserver = MutableLiveData<String>(alarmSettings.stopTimeAtUi)


    val _startTimeAtUi: LiveData<String> = startTimeAtUiObserver
    val _stopTimeAtUi: LiveData<String> = stopTimeAtUiObserver

    fun setStartTimeAtUi(hour: Int, minutes:Int) {
        startTimeAtUiObserver.value = "$hour:$minutes"
    }

    fun setStopTimeAtUi(hour: Int, minutes:Int) {
        stopTimeAtUiObserver.value = "$hour:$minutes"
    }



    fun getStartTimeAtUi(): LiveData<String> {
        return _startTimeAtUi
    }

    fun getStopTimeAtUi(): LiveData<String> {
        return _stopTimeAtUi
    }





    fun setTime(usage: Int, time: Calendar) {

        when (usage) {
            AlarmSettings.START_TIME ->
                alarmSettings.startTime = time
            AlarmSettings.STOP_TIME -> alarmSettings.stopTime = time
        }
    }

    fun getStartTime(): Calendar {
        return alarmSettings.startTime
    }

    fun getStopTime(): Calendar {
        return alarmSettings.stopTime
    }




}