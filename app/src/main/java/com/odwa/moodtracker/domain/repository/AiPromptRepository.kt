package com.odwa.moodtracker.domain.repository

import com.odwa.moodtracker.domain.model.Mood

interface AiPromptRepository {
    suspend fun getJournalingPrompt(mood: Mood, recentMoods: List<Mood>): Result<String>
}