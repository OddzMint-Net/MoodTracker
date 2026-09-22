package com.odwa.moodtracker.presentation.components

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.domain.model.LoggedMood
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.presentation.model.accent
import com.odwa.moodtracker.presentation.model.labelRes
import com.odwa.moodtracker.ui.preview.MoodTrackerAppPreview
import com.odwa.moodtracker.util.toReadableDateTime
import com.odwa.moodtracker.util.toShortDayLabel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoodHistoryItem(
    entry: LoggedMood,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val label = stringResource(entry.mood.labelRes)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(colors.surfaceVariant)
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .clearAndSetSemantics {
                contentDescription = "$label, ${entry.timestamp.toReadableDateTime()}"
            }
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(entry.mood.accent)
        )
        Text(
            text = entry.timestamp.toShortDayLabel(),
            style = MaterialTheme.typography.labelMedium,
            color = colors.onSurfaceVariant,
            maxLines = 1
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Happy")
@Composable
private fun MoodHistoryItemHappyPreview() {
    MoodTrackerAppPreview {
        MoodHistoryItem(
            entry = LoggedMood(
                mood = Mood.HAPPY,
                timestamp = System.currentTimeMillis()
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Strip of five")
@Composable
private fun MoodHistoryItemStripPreview() {
    val now = System.currentTimeMillis()
    val day = 24 * 60 * 60 * 1000L

    MoodTrackerAppPreview {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            listOf(Mood.HAPPY, Mood.NEUTRAL, Mood.SAD, Mood.ANGRY)
                .forEachIndexed { index, mood ->
                    MoodHistoryItem(
                        entry = LoggedMood(
                            mood = mood,
                            timestamp = now - (4 - index) * day
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MoodHistoryItemDarkPreview() {
    MoodTrackerAppPreview {
        MoodHistoryItem(
            entry = LoggedMood(
                mood = Mood.SAD,
                timestamp = System.currentTimeMillis()
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}