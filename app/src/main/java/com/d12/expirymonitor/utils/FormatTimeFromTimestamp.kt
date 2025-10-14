package com.d12.expirymonitor.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatTimeFromTimestamp(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(date)
}
