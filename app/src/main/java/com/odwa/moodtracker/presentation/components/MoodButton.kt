/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.odwa.moodtracker.ui.preview.MoodTrackerAppPreview

@Composable
fun MoodButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = false,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {

    val colors = MaterialTheme.colorScheme
    val border = colors.primary.copy(alpha = 0.12f).compositeOver(colors.primaryContainer)

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.primaryContainer,
            contentColor = colors.onPrimaryContainer,
            disabledContainerColor = colors.onSurface.copy(alpha = 0.08f),
            disabledContentColor = colors.onSurface.copy(alpha = 0.38f)
        ),

        border = BorderStroke(
            width = 1.5.dp,
            color = if (enabled) border else colors.onSurface.copy(alpha = .12f)
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp)
    )
    {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, name = "Default")
@Composable
private fun MoodButtonDefaultPreview() {
    MoodTrackerAppPreview {
        MoodButton(
            text = "Tell me what's going on...",
            onClick = {},
            enabled = true,
            icon = Icons.Outlined.Edit,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "Disabled")
@Composable
private fun MoodButtonDisabledPreview() {
    MoodTrackerAppPreview {
        MoodButton(
            text = "Tell me what's going on...",
            onClick = {},
            enabled = false,
            icon = Icons.Outlined.Edit,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "Enabled and disabled")
@Composable
private fun MoodButtonStatesPreview() {
    MoodTrackerAppPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MoodButton("Log mood Button", onClick = {}, enabled = true, icon = Icons.Outlined.Edit)
            MoodButton("Log mood Button", onClick = {}, enabled = false, icon = Icons.Outlined.Edit)
        }
    }
}

@Preview(showBackground = true, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MoodButtonDarkPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MoodButton("Tell me what's going on...", onClick = {}, enabled = true, icon = Icons.Outlined.Edit)
        MoodButton("Tell me what's going on...", onClick = {}, enabled = false, icon = Icons.Outlined.Edit)
    }
}

