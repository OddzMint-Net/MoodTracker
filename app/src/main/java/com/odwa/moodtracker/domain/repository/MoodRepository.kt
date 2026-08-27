package com.odwa.moodtracker.domain.repository

import com.odwa.moodtracker.domain.model.LoggedMood
import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    suspend fun savedMood(mood: LoggedMood)
    fun getAllMoodHistory(): Flow<List<LoggedMood>>
    suspend fun clearHistory()
    suspend fun getRecentMoods(limit: Int = 3): List<LoggedMood>
    suspend fun getMoodCount(): Int
}