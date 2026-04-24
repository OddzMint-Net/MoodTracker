package com.odwa.moodtracker.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odwa.moodtracker.R
import com.odwa.moodtracker.data.model.DefaultMoods
import com.odwa.moodtracker.data.model.Mood
import com.odwa.moodtracker.data.model.MoodEntry

@Composable
fun LogMoodContent(
    selectedMood: Mood?,
    history: List<MoodEntry>,
    onMoodSelected: (Mood) -> Unit,
    onSaveMood: () -> Unit,
    onClearHistory: () -> Unit,
    supportMessage: String?,
    modifier: Modifier = Modifier
) {
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
                text = stringResource(R.string.mood_message),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondary
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
                        modifier = Modifier.padding(8.dp)
                            .background(
                                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                            .selectable(selected = isSelected, onClick = { onMoodSelected(mood) })
                    ) {
                        Text(text = mood.emoji, fontSize = 32.sp)
                        Text(text = mood.label, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
            MoodButton(
                text = stringResource(R.string.log_mood),
                onClick = onSaveMood,
                enabled = selectedMood != null
            )
        }
        supportMessage?.let { message ->
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClearHistory) {
                    Text(text = stringResource(R.string.clear_history))
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