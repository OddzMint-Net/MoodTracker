package com.odwa.moodtracker.data.repository

import com.odwa.moodtracker.data.dao.MoodDao
import com.odwa.moodtracker.data.mapper.toDomain
import com.odwa.moodtracker.data.mapper.toEntity
import com.odwa.moodtracker.domain.repository.MoodRepository
import com.odwa.moodtracker.domain.model.LoggedMood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoodRepositoryImpl @Inject constructor(
    private val moodDao: MoodDao,
) : MoodRepository {

    override suspend fun savedMood(mood: LoggedMood) {
        moodDao.insertMood(mood.toEntity())
    }

    override fun getAllMoodHistory(): Flow<List<LoggedMood>> {
        return moodDao.getAllMoods().map { entries -> entries.map { it.toDomain() } }
    }

    override suspend fun clearHistory() {
        return moodDao.clearAll()
    }

    override suspend fun getRecentMoods(limit: Int): List<LoggedMood> {
        return moodDao.getRecentMoods(limit).map { it.toDomain() }
    }

    override suspend fun getMoodCount(): Int {
        return moodDao.getMoodCount()
    }
}