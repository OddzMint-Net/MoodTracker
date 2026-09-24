/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.domain.usecase

import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.domain.repository.AiPromptRepository
import com.odwa.moodtracker.domain.repository.MoodRepository
import javax.inject.Inject

class GetJournalingPromptUseCase @Inject constructor(
    private val moodRepository: MoodRepository,
    private val aiPromptRepository: AiPromptRepository
) {

    suspend operator fun invoke(mood: Mood): Result<String> {
        val recentMoods = moodRepository.getRecentMoods(limit = 5)
        val recentMoodValues = recentMoods.map { it.mood }
        return aiPromptRepository.getJournalingPrompt(mood, recentMoodValues)
    }
}