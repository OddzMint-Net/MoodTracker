/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.odwa.moodtracker.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
private val dateTimeFormatter = DateTimeFormatter.ofPattern("EE,dd MMM • HH:mm")

@RequiresApi(Build.VERSION_CODES.O)
private val shortDayFormatter = DateTimeFormatter.ofPattern("EEE")

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toReadableDateTime(): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
        .format(dateTimeFormatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toShortDayLabel(): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
        .format(shortDayFormatter)
}