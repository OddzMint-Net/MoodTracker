package com.odwa.moodtracker.data.remote

import android.util.Log
import com.odwa.moodtracker.BuildConfig
import com.odwa.moodtracker.data.model.MoodEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class GeminiService {

    suspend fun getJournalingPrompt(moodLabel: String, recentMoods: List<MoodEntry>): String {
        return withContext(Dispatchers.IO) {
            val apiKey = BuildConfig.GEMINI_API_KEY
            val url =
                URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey")
            val moodHistory = if (recentMoods.isNotEmpty()) {
                val moodList = recentMoods.joinToString(",") { it.label }
                "Over the past few days, the user has been feeling: $moodList."
            } else {
                ""
            }

            val prompt = """
                $moodHistory
                The user is currently feeling $moodLabel today.
                Write one short, warm and thoughtful journaling prompt to help them reflect on their feeling.
                Take their recent mood history into account if available.
                Keep it to two sentences maximum. Do not use bullet points. Speak directly to the user.
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
            Log.d("GeminiTest", "Calling URL: $url")
            Log.d("GeminiTest", "Request body: $requestBody")

            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                doOutput = true
                outputStream.write(requestBody.toByteArray())
            }

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
        }
    }
}