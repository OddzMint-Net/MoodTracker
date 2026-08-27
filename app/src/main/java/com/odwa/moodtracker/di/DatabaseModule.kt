package com.odwa.moodtracker.di

import android.content.Context
import androidx.room.Room
import com.odwa.moodtracker.data.dao.MoodDao
import com.odwa.moodtracker.data.database.MoodDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoodDatabase(@ApplicationContext context: Context): MoodDatabase {
        return Room.databaseBuilder(
            context, MoodDatabase::class.java, "mood_tracker_db"
        ).build()
    }

    @Provides
    fun provideMoodDao(database: MoodDatabase): MoodDao = database.moodDao()
}