//package com.d12.expirymonitor.viewmodel
//
//
//import android.content.Context
//import androidx.lifecycle.ViewModel
//import com.d12.expirymonitor.domain.usecase.ReminderScheduler
//import com.d12.expirymonitor.utils.getMillisFromDateTime
//
//
//class NotificationViewModel() : ViewModel() {
//
//    fun scheduleUserNotification(
//        context: Context,
//        title: String,
//        message: String,
//        year: Int,
//        month: Int,
//        day: Int,
//        hour: Int,
//        minute: Int
//    ) {
//        val scheduledMillis = getMillisFromDateTime(year, month, day, hour, minute)
//
//        ReminderScheduler.scheduleOneTimeReminder(
//            context = context,
//            title = title,
//            message = message,
//            scheduledTimeMillis = scheduledMillis
//        )
//    }
//
//}


package com.d12.expirymonitor.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.d12.expirymonitor.domain.usecase.ReminderScheduler
import com.d12.expirymonitor.utils.getMillisFromDateTime

/**
 * ✅ ViewModel handles user interactions and scheduling notifications.
 */
class NotificationViewModel : ViewModel() {

    fun scheduleUserNotification(
        context: Context,
        title: String,
        message: String,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int
    ) {
        val scheduledMillis = getMillisFromDateTime(year, month, day, hour, minute)

        ReminderScheduler.scheduleOneTimeReminder(
            context = context,
            title = title,
            message = message,
            scheduledTimeMillis = scheduledMillis
        )
    }
}
