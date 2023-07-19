package com.example.a15minutesreminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [AlarmSettings::class], version = 1)
abstract class AlarmSettingsDatabase: RoomDatabase() {


   abstract fun dao(): AlarmSettingsDao

   companion object {
      @Volatile
      private var INSTANCE: AlarmSettingsDatabase? = null

      fun getDatabase(context: Context): AlarmSettingsDatabase {
         val tempInstance = INSTANCE
         if (tempInstance != null) {
            return tempInstance
         }
         synchronized(this) {
            val instance = Room.databaseBuilder(
               context.applicationContext,
               AlarmSettingsDatabase::class.java,
               "alarm-settings-database"
            ).createFromAsset("database/alarm.db")
               .allowMainThreadQueries()
               .build()
            INSTANCE = instance
            return instance
         }
      }
   }
}