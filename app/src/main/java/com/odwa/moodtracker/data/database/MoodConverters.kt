package com.odwa.moodtracker.data.database

import androidx.room.TypeConverter
import com.odwa.moodtracker.domain.model.Mood

class MoodConverters {
    @TypeConverter
    fun fromMood(mood: Mood): String = mood.name

    @TypeConverter
    fun toMood(value: String): Mood = Mood.valueOf(value)
}