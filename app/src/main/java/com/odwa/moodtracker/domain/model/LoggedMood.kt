package com.odwa.moodtracker.domain.model

data class LoggedMood(
    val id: Long = 0,
    val emoji: String,
    val label: String,
    val timestamp: Long
)