package com.odwa.moodtracker.presentation.components

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.R
import com.odwa.moodtracker.domain.model.LoggedMood
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.ui.preview.MoodTrackerAppPreview

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogMoodContent(
    selectedMood: Mood?,
    moodForSupportMessage: Mood?,
    history: List<LoggedMood>,
    onMoodSelected: (Mood) -> Unit,
    onSaveMood: () -> Unit,
    onClearHistory: () -> Unit,
    supportMessage: String?,
    isLoadingSupportMessage: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    )
    {
        item { BrandRow() }
        item { Headline() }
        item {
            MoodPicker(
                moods = Mood.entries,
                selectedMood = selectedMood,
                onMoodSelected = onMoodSelected
            )
        }

        item {
            MoodButton(
                text = stringResource(R.string.log_mood),
                onClick = onSaveMood,
                enabled = selectedMood != null,
                icon = Icons.Outlined.Edit
            )
        }

        when {
            isLoadingSupportMessage -> item { SupportLoading() }
            supportMessage != null -> item {
                SupportCard(mood = moodForSupportMessage, message = supportMessage)
            }
        }
        item {
            HistorySection(
                history = history,
                onClearHistory = onClearHistory
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Empty state")
@Composable
private fun LogMoodContentEmptyPreview() {
    MoodTrackerAppPreview {
        LogMoodContent(
            selectedMood = null,
            moodForSupportMessage = null,
            history = emptyList(),
            onMoodSelected = {},
            onSaveMood = {},
            onClearHistory = {},
            supportMessage = null,
            isLoadingSupportMessage = false
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Loading support message")
@Composable
private fun LogMoodContentLoadingPreview() {
    MoodTrackerAppPreview {
        LogMoodContent(
            selectedMood = Mood.NEUTRAL,
            moodForSupportMessage = null,
            history = emptyList(),
            onMoodSelected = {},
            onSaveMood = {},
            onClearHistory = {},
            supportMessage = null,
            isLoadingSupportMessage = true
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Full - message and history")
@Composable
private fun LogMoodContentFullPreview() {
    val now = System.currentTimeMillis()
    val day = 24 * 60 * 60 * 1000L

    MoodTrackerAppPreview {
        LogMoodContent(
            selectedMood = null,
            moodForSupportMessage = Mood.NEUTRAL,
            history = listOf(
                LoggedMood(mood = Mood.HAPPY, timestamp = now - 4 * day),
                LoggedMood(mood = Mood.NEUTRAL, timestamp = now - 3 * day),
                LoggedMood(mood = Mood.SAD, timestamp = now - 2 * day),
                LoggedMood(mood = Mood.HAPPY, timestamp = now - 1 * day),
                LoggedMood(mood = Mood.ANGRY, timestamp = now)
            ),
            onMoodSelected = {},
            onSaveMood = {},
            onClearHistory = {},
            supportMessage = "Steady is worth noticing too. Nothing's pulling hard in either direction right now - want to log what's keeping it even?",
            isLoadingSupportMessage = false
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LogMoodContentDarkPreview() {
    val now = System.currentTimeMillis()
    val day = 24 * 60 * 60 * 1000L

    MoodTrackerAppPreview {
        LogMoodContent(
            selectedMood = null,
            moodForSupportMessage = Mood.SAD,
            history = listOf(
                LoggedMood(mood = Mood.SAD, timestamp = now - 1 * day),
                LoggedMood(mood = Mood.HAPPY, timestamp = now)
            ),
            onMoodSelected = {},
            onSaveMood = {},
            onClearHistory = {},
            supportMessage = "Feeling low is worth sitting with, not fixing right away.",
            isLoadingSupportMessage = false
        )
    }
}