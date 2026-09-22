package com.odwa.moodtracker.data.support

import com.odwa.moodtracker.domain.SupportMessageProvider
import com.odwa.moodtracker.domain.model.Mood
import javax.inject.Inject

class DefaultSupportMessageProvider @Inject constructor() : SupportMessageProvider {
    override fun getLocalSupportMessage(mood: Mood): String {
        return when (mood) {
            Mood.HAPPY -> "It’s good to notice the good moments. Hold onto this feeling."
            Mood.SAD -> "You showed up and logged how you feel. That already matters."
            Mood.ANGRY -> "Pause for a moment. Your feeling is valid, but it does not have to control your next step."
            Mood.NEUTRAL -> "Not every day needs to be dramatic. It's okay to just be."
        }
    }
}