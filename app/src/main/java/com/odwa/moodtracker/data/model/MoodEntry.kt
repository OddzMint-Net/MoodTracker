package com.odwa.moodtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val emoji: String,
    val label: String,
    val timestamp: Long
)