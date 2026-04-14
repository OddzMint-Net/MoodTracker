package com.odwa.moodtracker.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odwa.moodtracker.data.model.MoodEntry
import com.odwa.moodtracker.util.toReadableDateTime

@Composable
fun MoodHistoryItem(entry: MoodEntry) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(text = entry.emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.animateContentSize()) {
                Text(
                    text = entry.label,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = entry.timestamp.toReadableDateTime(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}