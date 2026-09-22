package com.odwa.moodtracker.presentation.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.odwa.moodtracker.R
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.ui.theme.MoodAngry
import com.odwa.moodtracker.ui.theme.MoodHappy
import com.odwa.moodtracker.ui.theme.MoodNeutral
import com.odwa.moodtracker.ui.theme.MoodSad

@get:StringRes
val Mood.labelRes: Int
    get() = when (this) {
        Mood.HAPPY -> R.string.happy_mood
        Mood.NEUTRAL -> R.string.neutral_mood
        Mood.SAD -> R.string.sad_mood
        Mood.ANGRY -> R.string.angry_mood
    }

val Mood.accent: Color
    get() = when (this) {
        Mood.HAPPY -> MoodHappy
        Mood.NEUTRAL -> MoodNeutral
        Mood.SAD -> MoodSad
        Mood.ANGRY -> MoodAngry
    }