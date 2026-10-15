package com.odwa.moodtracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odwa.moodtracker.domain.usecase.OnboardingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    onboardingUseCases: OnboardingUseCases
) : ViewModel() {

    val showOnboarding: StateFlow<Boolean?> = onboardingUseCases.hasCompletedOnboarding()
        .map { completed -> !completed }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}