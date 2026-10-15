package com.odwa.moodtracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odwa.moodtracker.domain.usecase.OnboardingUseCases
import com.odwa.moodtracker.presentation.onboarding.OnBoardingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onboardingUseCases: OnboardingUseCases
) : ViewModel() {

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            OnBoardingEvent.Complete -> completeOnboarding()
        }
    }

    private fun completeOnboarding() {
        viewModelScope.launch { onboardingUseCases.completeOnboarding() }
    }
}