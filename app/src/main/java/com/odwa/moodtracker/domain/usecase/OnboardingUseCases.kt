package com.odwa.moodtracker.domain.usecase

data class OnboardingUseCases(
    val hasCompletedOnboarding: HasCompletedOnboardingUseCase,
    val completeOnboarding: CompleteOnboardingUseCase
)