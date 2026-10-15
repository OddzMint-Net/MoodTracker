package com.odwa.moodtracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    suspend fun markOnBoardingCompleted()
    fun hasCompletedOnboarding(): Flow<Boolean>
}