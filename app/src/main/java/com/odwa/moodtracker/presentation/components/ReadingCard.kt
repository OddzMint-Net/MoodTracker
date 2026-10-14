package com.odwa.moodtracker.presentation.components

import android.os.Message
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.R
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.presentation.model.accent
import com.odwa.moodtracker.presentation.model.labelRes

@Composable
fun ReadingCard(
    mood: Mood?,
    message: String?,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {

    val colors = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(20.dp)

    val showMood = mood != null && mood != Mood.CUSTOM
    val title = if (showMood) {
        stringResource(R.string.reading_mood, stringResource(mood.labelRes))
    } else {
        stringResource(R.string.reading_generic) // to comeback
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(colors.surface)
            .border(1.dp, colors.outlineVariant, shape)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(mood?.accent ?: colors.outlineVariant)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = colors.onSurfaceVariant
            )
        }

        when {
            isLoading -> Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = colors.primary
                )

                Text(
                    text = stringResource(R.string.support_message_loading),
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onSurfaceVariant
                )
            }

            message != null -> Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onSurface
            )

            else -> Text(
                text = stringResource(R.string.reading_empty),
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@Preview(name = "Empty", showBackground = true)
@Composable
private fun ReadingCardEmptyPreview() {
    MaterialTheme {
        ReadingCard(
            mood = null,
            message = null,
            isLoading = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
private fun ReadingCardLoadingPreview() {
    MaterialTheme {
        ReadingCard(
            mood = Mood.HAPPY,
            message = null,
            isLoading = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Message", showBackground = true)
@Composable
private fun ReadingCardMessagePreview() {
    MaterialTheme {
        ReadingCard(
            mood = Mood.CUSTOM,
            message = "Steady is worth noticing too. Nothing's pulling hard in either direction right now.",
            isLoading = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Custom feeling", showBackground = true)
@Composable
private fun ReadingCardCustomPreview() {
    MaterialTheme {
        ReadingCard(
            mood = Mood.CUSTOM,
            message = "Feeling tired and hopeful at once is a lot to carry. What is the hope attached to?",
            isLoading = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}