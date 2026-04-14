package com.odwa.moodtracker.data.repository

import com.odwa.moodtracker.data.dao.MoodDao
import com.odwa.moodtracker.data.model.MoodEntry
import kotlinx.coroutines.flow.Flow

class MoodRepository(
    private val moodDao: MoodDao,
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

    suspend fun getRecentMoods(limit: Int = 3): List<MoodEntry> {
        return moodDao.getRecentMoods(limit)
    }
}