package com.odwa.moodtracker.data.mapper

import com.odwa.moodtracker.data.database.MoodEntry
import com.odwa.moodtracker.domain.model.LoggedMood

fun MoodEntry.toDomain(): LoggedMood = LoggedMood(
    id = id,
    emoji = emoji,
    label = label,
    timestamp = timestamp
)

fun LoggedMood.toEntity(): MoodEntry = MoodEntry(
    id = id,
    emoji = emoji,
    label = label,
    timestamp = timestamp
)