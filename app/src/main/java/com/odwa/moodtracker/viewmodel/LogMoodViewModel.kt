package com.odwa.moodtracker.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odwa.moodtracker.data.model.Mood
import com.odwa.moodtracker.data.model.MoodEntry
import com.odwa.moodtracker.data.repository.MoodRepository
import com.odwa.moodtracker.util.SupportMessageProvider
import com.odwa.moodtracker.util.SupportMessageProvider.getLocalSupportMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LogMoodViewModel(
    private val repository: MoodRepository
) : ViewModel() {
    private val _supportMessage = MutableStateFlow<String?>(null)
    val supportMessage: StateFlow<String?> = _supportMessage.asStateFlow()

    private val _selectedMood = MutableStateFlow<Mood?>(null)
    val selectedMood: StateFlow<Mood?> = _selectedMood

    init {
        logHistory()
    }

    fun selectMood(mood: Mood) {
        _selectedMood.value = mood
    }

    val moodHistory = repository.getAllMoodHistory()
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = emptyList())

    fun saveMood() {
        val mood = _selectedMood.value ?: return
        val entry = MoodEntry(
            emoji = mood.emoji,
            label = mood.label,
            timestamp = System.currentTimeMillis()
        )
        viewModelScope.launch {
            repository.saveMood(entry)

            _supportMessage.value = null

            try {
                val aiMessage = repository.getAiJournalingPrompt(mood.label)
                _supportMessage.value = aiMessage
            } catch (e: Exception) {
                Log.e("MoodTracker", "AI prompt failed", e)
                _supportMessage.value = SupportMessageProvider.getLocalSupportMessage(mood.label)
            }
            _selectedMood.value = null
        }
    }

    fun logHistory() {
        viewModelScope.launch {
            repository.getAllMoodHistory().collect { list ->
                Log.d("MoodTracker", "History size: ${list.size}")
                list.forEach {
                    Log.d("MoodTracker", it.toString())
                }
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}
