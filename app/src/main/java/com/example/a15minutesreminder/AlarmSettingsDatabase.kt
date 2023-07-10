package com.example.a15minutesreminder

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [AlarmSettings::class], version = 1)
abstract class AlarmSettingsDatabase: RoomDatabase() {


   abstract fun dao(): AlarmSettingsDao
}