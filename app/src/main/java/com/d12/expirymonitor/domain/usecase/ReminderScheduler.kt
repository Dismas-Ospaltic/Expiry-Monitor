//package com.d12.expirymonitor.domain.usecase
//
//
//import android.content.Context
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkManager
//import androidx.work.workDataOf
//import com.d12.expirymonitor.worker.ReminderWorker
//import java.util.concurrent.TimeUnit
//
//object ReminderScheduler {
//    fun scheduleOneTimeReminder(
//        context: Context,
//        title: String,
//        message: String,
//        scheduledTimeMillis: Long
//    ) {
//        val currentTimeMillis = System.currentTimeMillis()
//        val delayMillis = scheduledTimeMillis - currentTimeMillis
//
//        if (delayMillis <= 0) return // ⛔ Ignore past time
//
//        val data = workDataOf(
//            "title" to title,
//            "message" to message
//        )
//
//        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
//            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
//            .setInputData(data)
//            .build()
//
//        WorkManager.getInstance(context)
//            .enqueue(workRequest)
//    }
//
//
//}

package com.d12.expirymonitor.domain.usecase

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.d12.expirymonitor.worker.ReminderWorker
import java.util.concurrent.TimeUnit

/**
 * ✅ Handles scheduling a one-time reminder using WorkManager.
 * Keeps business logic outside of ViewModel.
 */
object ReminderScheduler {

    fun scheduleOneTimeReminder(
        context: Context,
        title: String,
        message: String,
        scheduledTimeMillis: Long
    ) {
        val delayMillis = scheduledTimeMillis - System.currentTimeMillis()

        // ⛔ Ignore scheduling for past dates
        if (delayMillis <= 0) return

        // 📦 Prepare input data for the Worker
        val data = workDataOf(
            "title" to title,
            "message" to message
        )

        // 🕒 Schedule the Work
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
