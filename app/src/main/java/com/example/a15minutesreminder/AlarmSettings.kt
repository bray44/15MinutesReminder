package com.example.a15minutesreminder

import androidx.lifecycle.LiveData
import androidx.room.Entity
import java.util.Calendar

@Entity
data class AlarmSettings(
    var startTime: Calendar,
    var stopTime: Calendar,
    var startTimeAtUi: String,
    var stopTimeAtUi: String,
) {
    companion object {

            const val START_TIME = 1
            const val STOP_TIME = 2

    }


}