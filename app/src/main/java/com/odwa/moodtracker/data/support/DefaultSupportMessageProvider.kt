package com.odwa.moodtracker.data.support

import com.odwa.moodtracker.domain.SupportMessageProvider
import javax.inject.Inject

class DefaultSupportMessageProvider @Inject constructor() : SupportMessageProvider {
    override fun getLocalSupportMessage(moodLabel: String): String {
        return when (moodLabel.lowercase()) {
            "happy" -> "It’s good to notice the good moments. Hold onto this feeling."
            "sad" -> "You showed up and logged how you feel. That already matters."
            "angry" -> "Pause for a moment. Your feeling is valid, but it does not have to control your next step."
            "neutral" -> "Not every day needs to be dramatic. It's okay to just be."
            else -> "Thank you for checking in with yourself today."
        }
    }
}