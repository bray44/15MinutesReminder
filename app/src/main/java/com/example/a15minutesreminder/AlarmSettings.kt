package com.example.a15minutesreminder

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity (tableName = "alarm_settings")
data class AlarmSettings(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "start_time" ) var startTime: Long,
    @ColumnInfo(name = "stop_time" ) var stopTime: Long,
    @ColumnInfo(name = "start_time_at_ui" ) var startTimeAtUi: List<Int>,
    @ColumnInfo(name = "stop_time_at_ui" ) var stopTimeAtUi:  List<Int>,
) {
    companion object {

            const val START_TIME = 1
            const val STOP_TIME = 2

    }


}