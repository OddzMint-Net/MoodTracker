package com.odwa.moodtracker.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odwa.moodtracker.data.model.Mood
import com.odwa.moodtracker.data.model.MoodEntry
import com.odwa.moodtracker.data.repository.MoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LogMoodViewModel(
    private val repository: MoodRepository
) : ViewModel() {

    init {
        logHistory()
    }

    private val _selectedMood = MutableStateFlow<Mood?>(null)
    val selectedMood: StateFlow<Mood?> = _selectedMood

    private val _journalingPrompt = MutableStateFlow<String?>(null)
    val journalingPrompt: StateFlow<String?> = _journalingPrompt

    private val _isLoadingPrompt = MutableStateFlow(false)
    val isLoadingPrompt: StateFlow<Boolean> = _isLoadingPrompt

    private val _promptError = MutableStateFlow<String?>(null)
    val promptError: StateFlow<String?> = _promptError


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
            Log.d("MoodTracker", "Saved mood: $entry")
            _selectedMood.value = null
            // Fetch journaling prompt automatically
            _isLoadingPrompt.value = true
            _promptError.value = null
            try {
                val recentMoods = repository.getRecentMoods(3)
                val totalEntries = recentMoods.size
                if (totalEntries >=3 && totalEntries % 3 ==0) {
                    Log.d("MoodTracker", "Recent moods passed to Gemini : ${recentMoods.map { it.label }}")
                    val prompt = repository.getJournalingPrompt(mood.label, recentMoods)
                    _journalingPrompt.value = prompt
                } else {
                    _journalingPrompt.value =
                        "Keep logging your moods. After a few entries I'll be able to share personalised reflections with you."
                }


            } catch (e: Exception) {
                Log.e("GeminiTest", "Error fetching prompt", e)
                _promptError.value = "Could not load a prompt right now. Try again later."
            } finally {
                _isLoadingPrompt.value = false
            }
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