package com.odwa.moodtracker.presentation.components

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odwa.moodtracker.R
import com.odwa.moodtracker.data.database.MoodEntry
import com.odwa.moodtracker.domain.model.MoodOption
import com.odwa.moodtracker.domain.DefaultMoods
import com.odwa.moodtracker.domain.model.LoggedMood
import com.odwa.moodtracker.ui.theme.MoodTrackerTheme

@Composable
fun LogMoodContent(
    selectedMood: MoodOption?,
    history: List<LoggedMood>,
    onMoodSelected: (MoodOption) -> Unit,
    onSaveMood: () -> Unit,
    onClearHistory: () -> Unit,
    supportMessage: String?,
    isLoadingSupportMessage: Boolean,
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

        if (isLoadingSupportMessage) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.support_message_loading),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
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

@Preview(showBackground = true)
@Composable
private fun LogMoodContentPreview() {
    MoodTrackerTheme {
        LogMoodContent(
            selectedMood = DefaultMoods.first(), history = listOf(
                LoggedMood(id = 1, emoji = "🙂", label = "Happy", timestamp = System.currentTimeMillis()),
                LoggedMood(id = 2, emoji = "😔", label = "Sad", timestamp = System.currentTimeMillis())
            ),
            onMoodSelected = {},
            onSaveMood = {},
            onClearHistory = {},
            supportMessage = "Thank you for checking in with yourself today,",
            isLoadingSupportMessage = false
        )
    }
}