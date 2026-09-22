package com.odwa.moodtracker.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.R
import com.odwa.moodtracker.domain.model.LoggedMood
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.ui.preview.MoodTrackerAppPreview

private const val HISTORY_PREVIEW_COUNT = 5

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistorySection(
    history: List<LoggedMood>,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val recent = history.take(HISTORY_PREVIEW_COUNT)

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.last_checks, HISTORY_PREVIEW_COUNT),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = colors.onSurface
            )
            if (recent.isNotEmpty()) {
                TextButton(onClick = onClearHistory) {
                    Text(
                        text = stringResource(R.string.clear_history),
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.primary
                    )
                }
            }
        }

        if (recent.isEmpty()) {
            Text(
                text = stringResource(R.string.history_empty),
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurfaceVariant
            )
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                recent.forEach { entry ->
                    MoodHistoryItem(entry = entry, modifier = Modifier.weight(1f))
                }
                repeat(HISTORY_PREVIEW_COUNT - recent.size) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Five entries")
@Composable
private fun HistorySectionFullPreview() {
    val now = System.currentTimeMillis()
    val day = 24 * 60 * 60 * 1000L
    MoodTrackerAppPreview {
        HistorySection(
            history = listOf(
                LoggedMood(mood = Mood.HAPPY, timestamp = now - 4 * day),
                LoggedMood(mood = Mood.NEUTRAL, timestamp = now - 3 * day),
                LoggedMood(mood = Mood.SAD, timestamp = now - 2 * day),
                LoggedMood(mood = Mood.ANGRY, timestamp = now - 1 * day),
                LoggedMood(mood = Mood.HAPPY, timestamp = now)
            ),
            onClearHistory = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Empty")
@Composable
private fun HistorySectionEmptyPreview() {
    MoodTrackerAppPreview {
        HistorySection(
            history = listOf(),
            onClearHistory = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}