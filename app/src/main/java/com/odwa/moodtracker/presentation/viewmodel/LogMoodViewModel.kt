package com.odwa.moodtracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odwa.moodtracker.domain.SupportMessageProvider
import com.odwa.moodtracker.domain.model.LoggedMood
import com.odwa.moodtracker.domain.model.MoodOption
import com.odwa.moodtracker.domain.repository.MoodRepository
import com.odwa.moodtracker.domain.usecase.GetJournalingPromptUseCase
import com.odwa.moodtracker.domain.usecase.LogMoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _selectedMood = MutableStateFlow<MoodOption?>(null)
    val selectedMood: StateFlow<MoodOption?> = _selectedMood.asStateFlow()
    val moodHistory: StateFlow<List<LoggedMood>> = moodRepository.getAllMoodHistory()
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = emptyList())

    fun selectMood(moodOption: MoodOption) {
        _selectedMood.value = moodOption
    }

    fun saveMood() {
        val moodOption = _selectedMood.value ?: return
        viewModelScope.launch {
            logMoodUseCase(moodOption)
            _supportMessage.value = null
            _isLoadingSupportMessage.value = true
            getJournalingPromptUseCase(moodOption.label)
                .onSuccess { prompt -> _supportMessage.value = prompt }
                .onFailure {
                    _supportMessage.value = supportMessageProvider.getLocalSupportMessage(moodOption.label) }
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