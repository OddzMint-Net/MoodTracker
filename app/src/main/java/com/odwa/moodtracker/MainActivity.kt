package com.odwa.moodtracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odwa.moodtracker.ui.theme.MoodTrackerTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.odwa.moodtracker.data.model.DefaultMoods
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.odwa.moodtracker.data.database.DatabaseProvider
import com.odwa.moodtracker.data.model.Mood
import com.odwa.moodtracker.data.remote.GeminiService
import com.odwa.moodtracker.data.repository.MoodRepository
import com.odwa.moodtracker.ui.components.MoodHistoryItem
import com.odwa.moodtracker.viewmodel.LogMoodViewModel
import com.odwa.moodtracker.viewmodel.LogMoodViewModelFactory


class MainActivity : ComponentActivity() {
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
                                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }, colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFFFFE0B2)
                        )
                    )
                }) { paddingValues ->
                    LogMoodScreen(modifier = Modifier.padding(paddingValues))
                    Log.d("GeminiTest", "API key: ${BuildConfig.GEMINI_API_KEY}")

                }
            }
        }
    }
}

@Composable
fun LogMoodScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val database = remember { DatabaseProvider.getDatabase(context) }
    val repository = remember { MoodRepository(database.moodDao(), geminiService = GeminiService()) }
    val viewModel: LogMoodViewModel = viewModel(factory = LogMoodViewModelFactory(repository))

    val selectedMood by viewModel.selectedMood.collectAsState()
    val history by viewModel.moodHistory.collectAsState()
    val journalingPrompt by viewModel.journalingPrompt.collectAsState()
    val isLoadingPrompt by viewModel.isLoadingPrompt.collectAsState()
    val promptError by viewModel.promptError.collectAsState()

    val moods = DefaultMoods

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    )
    {
        item {
            Text(
                text = "How are you feeling right now?",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.selectableGroup()) {
                moods.forEach { mood ->
                    val isSelected = selectedMood == mood
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(
                                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                else
                                    Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                            .selectable(
                                selected = isSelected,
                                onClick = { viewModel.selectMood(mood) }
                            ))

                    {
                        Text(text = mood.emoji, fontSize = 32.sp)
                        Text(text = mood.label, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.saveMood() },
                enabled = selectedMood != null
            ) {
                Text("Save Mood")
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            when {
                isLoadingPrompt -> {
                    CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                }

                promptError != null -> {
                    Text(
                        text = promptError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                journalingPrompt != null -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Reflect on this \uD83D\uDCAD",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = journalingPrompt!!,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = viewModel::clearHistory,
                ) {
                    Text("Clear History")
                }

            }
        }
        items(
            items = history,
            key = { it.id }) { entry ->
            MoodHistoryItem(entry)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoodTrackerTheme {
        LogMoodScreen()
    }
}