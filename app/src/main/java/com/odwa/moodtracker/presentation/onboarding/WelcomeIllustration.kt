package com.odwa.moodtracker.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.presentation.model.accent
import com.odwa.moodtracker.ui.theme.MoodTrackerTheme

@Composable
fun WelcomeIllustration() {
    val colors = MaterialTheme.colorScheme
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(colors.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "U",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = colors.onPrimary
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Mood.entries.filter { it != Mood.CUSTOM }.forEach { mood ->
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(mood.accent)
                )
            }
        }
    }
}


@Preview(name = "Welcome", showBackground = true)
@Composable
private fun WelcomeIllustrationPreview() {
    MoodTrackerTheme {
        PreviewIllustrationBox {
            WelcomeIllustration()
        }
    }
}

@Preview(name = "Reading", showBackground = true)
@Composable
private fun ReadingIllustrationPreview() {
    MoodTrackerTheme {
        PreviewIllustrationBox {
            ReadingIllustration()
        }
    }
}

@Preview(name = "History", showBackground = true)
@Composable
private fun HistoryIllustrationPreview() {
    MoodTrackerTheme {
        PreviewIllustrationBox {
            HistoryIllustration()
        }
    }
}

@Composable
private fun PreviewIllustrationBox(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .height(280.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) { content() }

}