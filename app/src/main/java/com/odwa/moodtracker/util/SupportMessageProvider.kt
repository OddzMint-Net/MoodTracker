package com.odwa.moodtracker.util

object SupportMessageProvider {
    fun getLocalSupportMessage(moodLabel: String): String {
        return when (moodLabel.lowercase()) {
            "happy" -> "It’s good to notice the good moments. Hold onto this feeling."
            "sad" -> "You showed up and logged how you feel. That already matters."
            "angry" -> "Pause for a moment. Your feeling is valid, but it does not have to control your next step."
            "anxious" -> "Take one slow breath. You’re allowed to move through this one step at a time."
            else -> "Thank you for checking in with yourself today."
        }
    }
}