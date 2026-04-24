package com.odwa.moodtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.odwa.moodtracker.ui.theme.MoodTrackerTheme
import androidx.compose.ui.text.font.FontWeight
import com.odwa.moodtracker.data.database.DatabaseProvider
import com.odwa.moodtracker.data.remote.ai.GeminiService
import com.odwa.moodtracker.data.repository.MoodRepository
import com.odwa.moodtracker.ui.components.LogMoodScreen
import com.odwa.moodtracker.viewmodel.LogMoodViewModel
import com.odwa.moodtracker.viewmodel.LogMoodViewModelFactory


class MainActivity : ComponentActivity() {
    private val database by lazy {
        DatabaseProvider.getDatabase(applicationContext)
    }

    private val repository by lazy {
        MoodRepository(
            moodDao = database.moodDao(),
            geminiService = GeminiService()
        )
    }

    private val viewModel: LogMoodViewModel by viewModels {
        LogMoodViewModelFactory(repository)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodTrackerTheme {
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Mood Tracker",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSecondary
                                )
                            )
                        }, colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            scrolledContainerColor = MaterialTheme.colorScheme.onTertiary
                        )
                    )
                })
                { paddingValues ->
                    LogMoodScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MoodTrackerTheme {
            LogMoodScreen(viewModel, modifier = Modifier)
        }
    }
}