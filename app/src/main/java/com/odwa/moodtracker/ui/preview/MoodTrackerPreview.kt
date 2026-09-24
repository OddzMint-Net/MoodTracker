/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.ui.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.odwa.moodtracker.ui.theme.MoodTrackerTheme

@Composable
fun MoodTrackerAppPreview(content: @Composable () -> Unit) {
    MoodTrackerTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}