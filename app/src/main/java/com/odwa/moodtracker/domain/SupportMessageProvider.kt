/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.domain

import com.odwa.moodtracker.domain.model.Mood

interface SupportMessageProvider {
    fun getLocalSupportMessage(mood: Mood): String
}