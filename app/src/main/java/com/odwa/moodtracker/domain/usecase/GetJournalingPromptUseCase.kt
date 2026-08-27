package com.odwa.moodtracker.domain.usecase

import com.odwa.moodtracker.domain.repository.AiPromptRepository
import com.odwa.moodtracker.domain.repository.MoodRepository
import javax.inject.Inject

class GetJournalingPromptUseCase @Inject constructor(
    private val moodRepository: MoodRepository,
    private val aiPromptRepository: AiPromptRepository
) {

    suspend operator fun invoke(moodLabel: String): Result<String> {
        val recentMoods = moodRepository.getRecentMoods(limit = 5)
        val recentMoodLabels = recentMoods.map { it.label }
        return aiPromptRepository.getJournalingPrompt(moodLabel, recentMoodLabels)
    }
}