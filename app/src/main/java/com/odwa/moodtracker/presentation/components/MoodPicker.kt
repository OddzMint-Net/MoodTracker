package com.odwa.moodtracker.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.domain.model.Mood
import com.odwa.moodtracker.presentation.model.accent
import com.odwa.moodtracker.presentation.model.labelRes
import com.odwa.moodtracker.ui.preview.MoodTrackerAppPreview

@Composable
fun MoodPicker(
    moods: List<Mood>,
    selectedMood: Mood?,
    onMoodSelected: (Mood) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        moods.forEach { mood ->
            MoodCard(
                mood = mood,
                selected = selectedMood == mood,
                onClick = { onMoodSelected(mood) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MoodCard(
    mood: Mood,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(14.dp)
    val label = stringResource(mood.labelRes)

    val container by animateColorAsState(
        targetValue = if (selected) colors.primary else colors.surface,
        label = "moodCardStroke"
    )
    val stroke by animateColorAsState(
        targetValue = if (selected) colors.primary else colors.outlineVariant,
        label = "moodCardStroke"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .clip(shape)
            .background(container)
            .border(BorderStroke(if (selected) 1.5.dp else 1.dp, stroke), shape)
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick
            )
            .padding(vertical = 14.dp, horizontal = 6.dp)
            .clearAndSetSemantics { contentDescription = label }
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(mood.accent)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = colors.onSurface,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, name = "Non selected")
@Composable
private fun MoodPickerPreview() {
    MoodTrackerAppPreview {
        MoodPicker(
            moods = Mood.entries,
            selectedMood = null,
            onMoodSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "Neutral selected")
@Composable
private fun MoodPickerSelectedPreview() {
    MoodTrackerAppPreview {
        MoodPicker(
            moods = Mood.entries,
            selectedMood = Mood.NEUTRAL,
            onMoodSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MoodPickerDarkPreview() {
    MoodTrackerAppPreview {
        MoodPicker(
            moods = Mood.entries,
            selectedMood = Mood.SAD,
            onMoodSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}