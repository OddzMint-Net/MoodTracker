package com.odwa.moodtracker.domain

interface SupportMessageProvider {
    fun getLocalSupportMessage(moodLabel: String): String
}