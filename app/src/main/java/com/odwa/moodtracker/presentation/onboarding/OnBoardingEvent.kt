package com.odwa.moodtracker.presentation.onboarding

sealed class OnBoardingEvent {
    data object Complete : OnBoardingEvent()
}