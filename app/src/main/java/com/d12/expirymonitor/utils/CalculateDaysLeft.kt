package com.d12.expirymonitor.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun calculateDaysRemaining(expiryDate: String): Int {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val expiry = formatter.parse(expiryDate)
        val today = Date()
        val diff = expiry.time - today.time
        (diff / (1000 * 60 * 60 * 24)).toInt()
    } catch (e: Exception) {
        0
    }
}
