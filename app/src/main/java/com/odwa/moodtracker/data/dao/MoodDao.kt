package com.odwa.moodtracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.odwa.moodtracker.data.model.MoodEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(entry: MoodEntry)

    @Query("SELECT * FROM mood_entries ORDER BY timestamp DESC")
    fun getAllMoods(): Flow<List<MoodEntry>>

    @Query("DELETE FROM mood_entries")
    suspend fun clearAll()

    @Query("SELECT * FROM mood_entries ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentMoods(limit: Int): List<MoodEntry>

    @Query("SELECT COUNT(*) FROM mood_entries")
    suspend fun getMoodCount(): Int
}