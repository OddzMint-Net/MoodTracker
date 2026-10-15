package com.odwa.moodtracker.domain.usecase

import com.odwa.moodtracker.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow

class HasCompletedOnboardingUseCase(private val repository: OnboardingRepository) {
    operator fun invoke(): Flow<Boolean> = repository.hasCompletedOnboarding()
}