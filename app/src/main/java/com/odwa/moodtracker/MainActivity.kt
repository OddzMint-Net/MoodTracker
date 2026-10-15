/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.odwa.moodtracker.presentation.components.LogMoodScreen
import com.odwa.moodtracker.presentation.viewmodel.MainViewModel
import com.odwa.moodtracker.ui.theme.MoodTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)

        splash.setKeepOnScreenCondition { viewModel.showOnboarding.value == null }
        setContent {
            MoodTrackerTheme {
                val showOnboarding by viewModel.showOnboarding.collectAsState()

                when (showOnboarding) {
                    true -> Text("Onboarding screen goes here")
                    false -> LogMoodScreen()
                    null -> Unit
                }
                LogMoodScreen()
            }
        }
    }
}