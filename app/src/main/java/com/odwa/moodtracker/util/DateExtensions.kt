package com.odwa.moodtracker.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toReadableDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("EE,dd MMM • HH:mm")
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
        .format(formatter)
}