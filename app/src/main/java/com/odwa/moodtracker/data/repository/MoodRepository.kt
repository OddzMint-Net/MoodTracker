package com.odwa.moodtracker.data.repository

import com.odwa.moodtracker.data.dao.MoodDao
import com.odwa.moodtracker.data.model.MoodEntry
import com.odwa.moodtracker.data.remote.GeminiService
import kotlinx.coroutines.flow.Flow

class MoodRepository(
    private val moodDao: MoodDao,
    private val geminiService: GeminiService
) {
    suspend fun saveMood(entry: MoodEntry) {
        moodDao.insertMood(entry)
    }

    fun getAllMoodHistory(): Flow<List<MoodEntry>> {
        return moodDao.getAllMoods()
    }

    suspend fun clearHistory() {
        moodDao.clearAll()
    }

    suspend fun getJournalingPrompt(moodLabel: String,recentMoods: List<MoodEntry>): String {
        return geminiService.getJournalingPrompt(moodLabel,recentMoods)
    }

    suspend fun getRecentMoods(limit: Int = 3): List<MoodEntry> {
        return moodDao.getRecentMoods(limit)
    }
}