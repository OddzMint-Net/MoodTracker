package com.odwa.moodtracker.presentation.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.ui.theme.MoodTrackerTheme

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(colors.primary.copy(alpha = 0.06f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        )
        {
            page.illustration()
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)){
            Text(
                text = stringResource(page.title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colors.onSurface
            )
            page.accent?.let {
                Text(
                    text = stringResource(it),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = colors.primary
                )
            }
        }
        Text(
            text = stringResource(page.body),
            style = MaterialTheme.typography.bodyLarge,
            color = colors.onSurfaceVariant
        )
    }
}

@Preview(name = "Page 1", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun OnboardingPage1Preview() {
    MoodTrackerTheme(dynamicColor = false) {
        Box(Modifier.padding(24.dp)) { OnboardingPageContent(onboardingPages[0]) }
    }
}

@Preview(name = "Page 2", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun OnboardingPage2Preview() {
    MoodTrackerTheme(dynamicColor = false) {
        Box(Modifier.padding(24.dp)) { OnboardingPageContent(onboardingPages[1]) }
    }
}

@Preview(name = "Page 3", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun OnboardingPage3Preview() {
    MoodTrackerTheme(dynamicColor = false) {
        Box(Modifier.padding(24.dp)) { OnboardingPageContent(onboardingPages[2]) }
    }
}

@Preview(
    name = "Page 2 - dark",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun OnboardingPageDarkPreview() {
    MoodTrackerTheme(dynamicColor = false) {
        Box(Modifier.padding(24.dp)) { OnboardingPageContent(onboardingPages[1]) }
    }
}