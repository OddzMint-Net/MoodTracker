package com.odwa.moodtracker.domain.model

data class LoggedMood(
    val id: Long = 0,
    val mood: Mood,
    val timestamp: Long
)