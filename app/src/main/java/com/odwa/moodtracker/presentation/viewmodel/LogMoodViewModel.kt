/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odwa.moodtracker.domain.SupportMessageProvider
import com.odwa.moodtracker.domain.model.LoggedMood
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.domain.repository.MoodRepository
import com.odwa.moodtracker.domain.usecase.GetJournalingPromptUseCase
import com.odwa.moodtracker.domain.usecase.LogMoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class LogMoodViewModel @Inject constructor(
    private val moodRepository: MoodRepository,
    private val logMoodUseCase: LogMoodUseCase,
    private val getJournalingPromptUseCase: GetJournalingPromptUseCase,
    private val supportMessageProvider: SupportMessageProvider
) : ViewModel() {

    private val _supportMessage = MutableStateFlow<String?>(null)
    val supportMessage: StateFlow<String?> = _supportMessage.asStateFlow()
    private val _isLoadingSupportMessage = MutableStateFlow(false)
    val isLoadingSupportMessage: StateFlow<Boolean> = _isLoadingSupportMessage.asStateFlow()
    private val _selectedMood = MutableStateFlow<Mood?>(null)
    val selectedMood: StateFlow<Mood?> = _selectedMood.asStateFlow()
    val moodHistory: StateFlow<List<LoggedMood>> = moodRepository.getAllMoodHistory()
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = emptyList())

    private val _moodForSupportMessage = MutableStateFlow<Mood?>(null)
    val moodForSupportMessage: StateFlow<Mood?> = _moodForSupportMessage.asStateFlow()
    private val _customText = MutableStateFlow("")
    val customText: StateFlow<String> = _customText.asStateFlow()

    private var messageJob: Job? = null

    fun selectMood(mood: Mood) {
        _selectedMood.value = mood
        requestSupportMessage(mood = mood)
    }

    fun onCustomTextChange(value: String) {
        _customText.value = value.take(140)
    }

    fun onSendCustom() {
        val text = _customText.value.trim()
        if (text.isEmpty()) return
        _selectedMood.value = null
        _customText.value = ""
        requestSupportMessage(mood = Mood.CUSTOM, customText = text)
    }

    private fun requestSupportMessage(mood: Mood, customText: String? = null) {
        messageJob?.cancel()
        messageJob = viewModelScope.launch {
            _supportMessage.value = null
            _moodForSupportMessage.value = mood
            _isLoadingSupportMessage.value = true

            val message = getJournalingPromptUseCase(mood, customText)
                .getOrElse { supportMessageProvider.getLocalSupportMessage(mood) }
            ensureActive()
            logMoodUseCase(mood, customText)
            _supportMessage.value = message
            _isLoadingSupportMessage.value = false
        }
    }

    fun saveMood() {
        val mood = _selectedMood.value ?: return
        viewModelScope.launch {
            logMoodUseCase(mood)
            _supportMessage.value = null
            _moodForSupportMessage.value = mood
            _isLoadingSupportMessage.value = true
            getJournalingPromptUseCase(mood)
                .onSuccess { prompt -> _supportMessage.value = prompt }
                .onFailure {
                    _supportMessage.value = supportMessageProvider.getLocalSupportMessage(mood)
                }
            _isLoadingSupportMessage.value = false
            _selectedMood.value = null
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            moodRepository.clearHistory()
        }
    }
}