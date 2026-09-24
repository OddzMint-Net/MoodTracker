/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.odwa.moodtracker.domain.model.Mood

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mood: Mood,
    val timestamp: Long
)