/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.odwa.moodtracker.data.dao.MoodDao

@Database(
    entities = [MoodEntry::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(MoodConverters::class)
abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
}