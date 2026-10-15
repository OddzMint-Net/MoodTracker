package com.odwa.moodtracker.domain.usecase

import com.odwa.moodtracker.domain.repository.OnboardingRepository

class CompleteOnboardingUseCase(private val repository: OnboardingRepository) {
    suspend operator fun invoke() = repository.markOnBoardingCompleted()
}