/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.odwa.moodtracker.presentation.viewmodel.LogMoodViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogMoodScreen(
    modifier: Modifier = Modifier,
    viewModel: LogMoodViewModel = hiltViewModel()
) {
    val selectedMood by viewModel.selectedMood.collectAsState()
    val moodForSupportMessage by viewModel.moodForSupportMessage.collectAsState()
    val history by viewModel.moodHistory.collectAsState()
    val supportMessage by viewModel.supportMessage.collectAsState()
    val isLoadingSupportMessage by viewModel.isLoadingSupportMessage.collectAsState()
    val customText by viewModel.customText.collectAsState()

    LogMoodContent(
        selectedMood = selectedMood,
        moodForSupportMessage = moodForSupportMessage,
        history = history,
        customText = customText,
        supportMessage = supportMessage,
        isLoadingSupportMessage = isLoadingSupportMessage,
        onMoodSelected = viewModel::selectMood,
        onCustomTextChange = viewModel::onCustomTextChange,
        onSendCustom = viewModel::onSendCustom,
        onClearHistory = viewModel::clearHistory,
        modifier = modifier
    )
}