package com.example.a15minutesreminder

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlarmSettingsDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarmSettings(alarmSettings: AlarmSettings)

    @Update
    suspend fun updateAlarmSettings(alarmSettings: AlarmSettings)

    @Query("SELECT * FROM alarm_settings WHERE id = 0")
    fun getAlarmSettings(): LiveData<AlarmSettings>





}