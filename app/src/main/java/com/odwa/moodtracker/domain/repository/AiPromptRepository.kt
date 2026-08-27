package com.odwa.moodtracker.domain.repository

interface AiPromptRepository {
    suspend fun getJournalingPrompt(moodLabel: String, recentMoodLabels: List<String>): Result<String>
}