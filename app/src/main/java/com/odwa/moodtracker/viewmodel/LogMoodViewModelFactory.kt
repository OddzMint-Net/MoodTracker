package com.odwa.moodtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.odwa.moodtracker.data.repository.MoodRepository

class LogMoodViewModelFactory(
    private val repository: MoodRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogMoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogMoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}