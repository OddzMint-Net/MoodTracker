package com.odwa.moodtracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import com.odwa.moodtracker.presentation.components.LogMoodScreen
import com.odwa.moodtracker.ui.theme.MoodTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodTrackerTheme {
                LogMoodScreen()
            }
        }
    }
}