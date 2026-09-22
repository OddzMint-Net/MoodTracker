package com.odwa.moodtracker.domain

import com.odwa.moodtracker.domain.model.Mood

interface SupportMessageProvider {
    fun getLocalSupportMessage(mood: Mood): String
}