package com.odwa.moodtracker.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: MoodDatabase? = null

    fun getDatabase(context: Context): MoodDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext, MoodDatabase::class.java, "mood_tracker_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}