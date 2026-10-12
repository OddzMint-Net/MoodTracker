/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.data.remote.ai

import android.util.Log
import com.odwa.moodtracker.BuildConfig
import com.odwa.moodtracker.domain.model.Mood
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class GeminiRemoteDataSource @Inject constructor() {


    suspend fun getJournalingPrompt(mood: Mood, recentMoods: List<Mood>, customText: String? = null): String {
        return withContext(Dispatchers.IO) {
            val apiKey = BuildConfig.GEMINI_API_KEY
            val url =
                URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey")
            val moodHistory = if (recentMoods.isNotEmpty()) {
                "Over the past few days, the user has been feeling: ${recentMoods.joinToString { it.promptLabel }}."
            } else {
                ""
            }

            val instruction: String
            val currentFeeling: String

            if (customText != null) {
                currentFeeling =
                    "The user describes how they are feeling right now in their own words: \"$customText\"."
                instruction = """
                    Respond directly to what they wrote with a short, warm, supportive reflection,
        then offer one gentle journaling prompt related to it.
        Three sentences maximum. Do not use bullet points.
        Do not diagnose or give medical advice.
                """.trimIndent()
            } else {
                currentFeeling = "The user is currently feeling ${mood.promptLabel} today."
                instruction = """
                    Write one short, warm and thoughtful journaling prompt to help them reflect on their feeling.
        Keep it to two sentences maximum. Do not use bullet points.
        Speak directly to the user and do not ask a question.
                """.trimIndent()
            }

            val prompt = """
                $moodHistory
                $currentFeeling
                Take their recent mood history into account if available.
                $instruction
            """.trimIndent()

            val requestBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                })
            }.toString()
            if (BuildConfig.DEBUG) {
                Log.d("GeminiTest", "Calling URL: $url")
            }

            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                connectTimeout = 10_000
                readTimeout = 15_000
                doOutput = true
                outputStream.write(requestBody.toByteArray())
            }

            try {
                val responseCode = connection.responseCode
                val response = if (responseCode == HttpURLConnection.HTTP_OK) {
                    connection.inputStream.bufferedReader().readText()
                } else {
                    val error = connection.errorStream?.bufferedReader()?.readText() ?: "Unknown error"
                    throw Exception("API Error $responseCode: $error")
                }

                val json = JSONObject(response)
                json.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text")
            } finally {
                connection.disconnect()
            }
        }
    }
}

private val Mood.promptLabel: String
    get() = when (this) {
        Mood.HAPPY -> "happy"
        Mood.NEUTRAL -> "neutral"
        Mood.SAD -> "sad"
        Mood.ANGRY -> "angry"
        Mood.CUSTOM -> "something you put into own words"
    }