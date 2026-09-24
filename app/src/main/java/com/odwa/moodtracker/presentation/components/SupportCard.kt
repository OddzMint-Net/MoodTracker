/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odwa.moodtracker.R
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.presentation.model.labelRes
import com.odwa.moodtracker.ui.preview.MoodTrackerAppPreview

@Composable
fun SupportCard(
    mood: Mood?,
    message: String,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(16.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(shape)
            .background(colors.surfaceContainerLow)
            .border(1.dp, colors.outlineVariant, shape)
            .padding(18.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(colors.primary)
        )
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = mood?.let { stringResource(R.string.reading_mood, stringResource(it.labelRes)) }
                    ?: stringResource(R.string.reading_generic),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = colors.onSurfaceVariant
            )
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = colors.onSurface
            )
        }
    }
}

@Preview(showBackground = true, name = "Support card")
@Composable
private fun SupportCardPreview() {
    MoodTrackerAppPreview {
        SupportCard(
            mood = Mood.NEUTRAL,
            message = "Steady is worth noticing too. Nothing's pulling hard in either direction right now — want to log what's keeping it even?",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SupportCardDarkPreview() {
    MoodTrackerAppPreview {
        SupportCard(
            mood = Mood.SAD,
            message = "Feeling low is worth sitting with, not fixing right away.",
            modifier = Modifier.padding(16.dp)

        )
    }
}