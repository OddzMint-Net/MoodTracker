/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.domain.model

data class LoggedMood(
    val id: Long = 0,
    val mood: Mood,
    val timestamp: Long
)