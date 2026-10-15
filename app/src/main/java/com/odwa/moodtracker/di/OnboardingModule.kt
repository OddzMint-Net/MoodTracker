package com.odwa.moodtracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.odwa.moodtracker.data.repository.OnboardingRepositoryImpl
import com.odwa.moodtracker.domain.repository.OnboardingRepository
import com.odwa.moodtracker.domain.usecase.CompleteOnboardingUseCase
import com.odwa.moodtracker.domain.usecase.HasCompletedOnboardingUseCase
import com.odwa.moodtracker.domain.usecase.OnboardingUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "onboarding_prefs")
@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideOnboardingRepository(
        dataStore: DataStore<Preferences>
    ): OnboardingRepository = OnboardingRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideOnboardingUseCases(repository: OnboardingRepository) = OnboardingUseCases(
        hasCompletedOnboarding = HasCompletedOnboardingUseCase(repository),
        completeOnboarding = CompleteOnboardingUseCase(repository)
    )
}