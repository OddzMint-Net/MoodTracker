/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.domain.usecase

import com.odwa.moodtracker.domain.model.LoggedMood
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.domain.repository.MoodRepository
import javax.inject.Inject

class LogMoodUseCase @Inject constructor(
    private val moodRepository: MoodRepository
) {
    suspend operator fun invoke(mood: Mood) {
        val loggedMood = LoggedMood(
            mood = mood,
            timestamp = System.currentTimeMillis()
        )
        moodRepository.savedMood(loggedMood)
    }
}