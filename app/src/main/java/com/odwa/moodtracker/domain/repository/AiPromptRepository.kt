/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.domain.repository

import com.odwa.moodtracker.domain.model.Mood

interface AiPromptRepository {
    suspend fun getJournalingPrompt(mood: Mood, recentMoods: List<Mood>): Result<String>
}