package com.example.a15minutesreminder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "alarm_settings")
data class AlarmSettings(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "alarm_status") var alarmStatus: Boolean,
    @ColumnInfo(name = "start_time" ) var startTime: Long,
    @ColumnInfo(name = "stop_time" ) var stopTime: Long,
    @ColumnInfo(name = "start_time_at_ui" ) var startTimeAtUi: String,
    @ColumnInfo(name = "stop_time_at_ui" ) var stopTimeAtUi:  String,
    @ColumnInfo(name = "interval_points") var intervalPoints: Int,
    @ColumnInfo(name = "mutable_interval_points") var mutableIntervalPoints: Int
) {
    companion object {
        const val START_TIME = 0
        const val STOP_TIME = 1
    }
}