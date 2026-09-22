package com.odwa.moodtracker.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.R
import com.odwa.moodtracker.ui.preview.MoodTrackerAppPreview

@Composable
fun Headline(
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = stringResource(R.string.app_name_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface
        )
        Text(
            text = stringResource(R.string.mood_line),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = colors.primary
        )
        Text(
            text = stringResource(R.string.check_in_message),
            style = MaterialTheme.typography.bodyLarge,
            color = colors.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, name = "HeadLine")
@Composable
private fun HeadlinePreview() {
    MoodTrackerAppPreview {
        Headline(modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HeadlineDarkPreview() {
    MoodTrackerAppPreview {
        Headline(modifier = Modifier.padding(16.dp))
    }
}