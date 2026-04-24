package com.odwa.moodtracker.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.odwa.moodtracker.viewmodel.LogMoodViewModel

@Composable
fun LogMoodScreen(
    viewModel: LogMoodViewModel,
    modifier: Modifier = Modifier
) {
    val selectedMood by viewModel.selectedMood.collectAsState()
    val history by viewModel.moodHistory.collectAsState()
    val supportMessage by viewModel.supportMessage.collectAsState()

    LogMoodContent(
        selectedMood = selectedMood,
        history = history,
        supportMessage = supportMessage,
        onMoodSelected = viewModel::selectMood,
        onSaveMood = viewModel::saveMood,
        onClearHistory = viewModel::clearHistory,
        modifier = modifier
    )
}