/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.data.mapper

import com.odwa.moodtracker.data.database.MoodEntry
import com.odwa.moodtracker.domain.model.LoggedMood

fun LoggedMood.toEntity(): MoodEntry = MoodEntry(
    id = id,
    mood = mood,
    timestamp = timestamp
)

fun MoodEntry.toDomain(): LoggedMood = LoggedMood(
    id = id,
    mood = mood,
    timestamp = timestamp
)