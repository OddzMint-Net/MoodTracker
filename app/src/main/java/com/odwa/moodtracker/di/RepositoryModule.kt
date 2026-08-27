package com.odwa.moodtracker.di

import com.odwa.moodtracker.data.repository.AiPromptRepositoryImpl
import com.odwa.moodtracker.data.repository.MoodRepositoryImpl
import com.odwa.moodtracker.domain.repository.AiPromptRepository
import com.odwa.moodtracker.domain.repository.MoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMoodRepository(moodRepositoryImpl: MoodRepositoryImpl): MoodRepository

    @Binds
    abstract fun bindAiPromptRepository(aiPromptRepositoryImpl: AiPromptRepositoryImpl): AiPromptRepository

}