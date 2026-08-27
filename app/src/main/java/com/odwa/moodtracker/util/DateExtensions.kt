package com.odwa.moodtracker.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
private val dateTimeFormatter = DateTimeFormatter.ofPattern("EE,dd MMM • HH:mm")

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toReadableDateTime(): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
        .format(dateTimeFormatter)
}