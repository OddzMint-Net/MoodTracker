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

    suspend fun getJournalingPrompt(mood: Mood, recentMoods: List<Mood>): String {
        return withContext(Dispatchers.IO) {
            val apiKey = BuildConfig.GEMINI_API_KEY
            val url =
                URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey")
            val moodHistory = if (recentMoods.isNotEmpty()) {
                "Over the past few days, the user has been feeling: ${recentMoods.joinToString { it.promptLabel }}."
            } else {
                ""
            }

            val prompt = """
                $moodHistory
                The user is currently feeling ${mood.promptLabel} today.
                Write one short, warm and thoughtful journaling prompt to help them reflect on their feeling.
                Take their recent mood history into account if available.
                Keep it to two sentences maximum. Do not use bullet points. Speak directly to the user and Do not ask a question.
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
    }