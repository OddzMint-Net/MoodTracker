package com.odwa.moodtracker.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.R
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.presentation.components.MoodPicker
import com.odwa.moodtracker.presentation.components.ReadingCard

@Composable
fun ReadingIllustration() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        MoodPicker(
            moods = Mood.entries.filter { it != Mood.CUSTOM },
            selectedMood = Mood.NEUTRAL,
            onMoodSelected = {}
        )

        ReadingCard(
            mood = Mood.NEUTRAL,
            message = stringResource(R.string.onboarding_sample_reading),
            isLoading = false
        )
    }
}