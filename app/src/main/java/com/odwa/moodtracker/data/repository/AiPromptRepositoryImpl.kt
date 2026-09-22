package com.odwa.moodtracker.data.repository

import com.odwa.moodtracker.data.remote.ai.GeminiRemoteDataSource
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.domain.repository.AiPromptRepository
import javax.inject.Inject

class AiPromptRepositoryImpl @Inject constructor(
    private val remoteDataSource: GeminiRemoteDataSource
) : AiPromptRepository {

    override suspend fun getJournalingPrompt(
        mood: Mood,
        recentMoods: List<Mood>
    ): Result<String> = runCatching {
        remoteDataSource.getJournalingPrompt(mood, recentMoods)
    }
}