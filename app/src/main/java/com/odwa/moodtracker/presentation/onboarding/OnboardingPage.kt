package com.odwa.moodtracker.presentation.onboarding

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.odwa.moodtracker.R

data class OnboardingPage(
    @StringRes val title: Int,
    @StringRes val accent: Int? = null,
    @StringRes val body: Int,
    val illustration: @Composable () -> Unit
)

 val onboardingPages = listOf(
    OnboardingPage(
        title = R.string.onboarding_title_1,
        accent = R.string.onboarding_accent_1,
        body = R.string.onboarding_body_1,
        illustration = { WelcomeIllustration() }
    ),
    OnboardingPage(
        title = R.string.onboarding_title_2,
        body = R.string.onboarding_body_2,
        illustration = { ReadingIllustration() }
    ),
    OnboardingPage(
        title = R.string.onboarding_title_3,
        body = R.string.onboarding_body_3,
        illustration = { HistoryIllustration() }
    )
)