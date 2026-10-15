package com.odwa.moodtracker.presentation.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.ui.theme.MoodTrackerTheme

@Composable
fun PageIndicator(
    count: Int,
    current: Int,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(count) { index ->
            val selected = index == current
            val width by animateDpAsState(if (selected) 24.dp else 8.dp, label = "dotWidth")
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(if (selected) colors.primary else colors.outlineVariant)
            )
        }
    }
}

@Preview(name = "Page 1", showBackground = true)
@Composable
private fun PageIndicatorFirstPreview() {
    MoodTrackerTheme {
        PageIndicator(
            count = 3,
            current = 0,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Page 3", showBackground = true)
@Composable
private fun PageIndicatorLastPreview() {
    MoodTrackerTheme {
        PageIndicator(
            count = 3,
            current = 2,
            modifier = Modifier.padding(16.dp)
        )
    }
}
