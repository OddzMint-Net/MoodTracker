package com.odwa.moodtracker.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.odwa.moodtracker.presentation.viewmodel.LogMoodViewModel

@Composable
fun LogMoodScreen(
    modifier: Modifier = Modifier,
    viewModel: LogMoodViewModel = hiltViewModel()
) {
    val selectedMood by viewModel.selectedMood.collectAsState()
    val history by viewModel.moodHistory.collectAsState()
    val supportMessage by viewModel.supportMessage.collectAsState()
    val isLoadingSupportMessage by viewModel.isLoadingSupportMessage.collectAsState()

    LogMoodContent(
        selectedMood = selectedMood,
        history = history,
        supportMessage = supportMessage,
        isLoadingSupportMessage = isLoadingSupportMessage,
        onMoodSelected = viewModel::selectMood,
        onSaveMood = viewModel::saveMood,
        onClearHistory = viewModel::clearHistory,
        modifier = modifier
    )
}