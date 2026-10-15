package com.odwa.moodtracker.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.R
import com.odwa.moodtracker.domain.model.LoggedMood
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.presentation.components.MoodHistoryItem

@Composable
fun HistoryIllustration() {
    val colors = MaterialTheme.colorScheme
    val sample = remember {
        val day = 24L * 60 * 60 * 1000
        val now = System.currentTimeMillis()
        listOf(Mood.HAPPY, Mood.NEUTRAL, Mood.SAD, Mood.HAPPY, Mood.ANGRY)
            .mapIndexed { index, mood ->
                LoggedMood(id = index.toLong(), mood = mood, timestamp = now - (4 - index) * day)
            }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            sample.forEach { MoodHistoryItem(entry = it, modifier = Modifier.weight(1f)) }
        }
        Text(
            text = stringResource(R.string.onboarding_history_caption),
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onSurfaceVariant
        )
    }
}