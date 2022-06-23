package com.example.cryptoapp_kotlin.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun getTimeFromMS(timestamp: Long?): String {
    val stamp = Timestamp(timestamp?.times(1000) ?: 0)
    val date = Date(stamp.time)
    val pattern = "HH:mm:ss"
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}