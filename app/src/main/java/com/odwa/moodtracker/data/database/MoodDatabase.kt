package com.odwa.moodtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.odwa.moodtracker.data.dao.MoodDao
import com.odwa.moodtracker.data.model.MoodEntry

@Database(
    entities = [MoodEntry::class],
    version = 1,
    exportSchema = false
)
abstract class MoodDatabase: RoomDatabase() {
    abstract fun moodDao(): MoodDao
}