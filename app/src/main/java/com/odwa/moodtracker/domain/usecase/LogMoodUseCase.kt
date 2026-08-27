package com.odwa.moodtracker.domain.usecase

import com.odwa.moodtracker.domain.model.LoggedMood
import com.odwa.moodtracker.domain.model.MoodOption
import com.odwa.moodtracker.domain.repository.MoodRepository
import javax.inject.Inject

class LogMoodUseCase @Inject constructor(
    private val moodRepository: MoodRepository
) {
    suspend operator fun invoke(moodOption: MoodOption) {
        val loggedMood = LoggedMood(
            emoji = moodOption.emoji,
            label = moodOption.label,
            timestamp = System.currentTimeMillis()
        )
        moodRepository.savedMood(loggedMood)
    }
}