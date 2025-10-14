//package com.d12.expirymonitor.worker
//
//
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.media.AudioAttributes
//import android.net.Uri
//import android.os.Build
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.core.content.ContextCompat
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import com.d12.expirymonitor.R
//
//class ReminderWorker(appContext: Context, workerParams: WorkerParameters) :
//    Worker(appContext, workerParams) {
//
//    override fun doWork(): Result {
//        val title = inputData.getString("title") ?: "Reminder"
//        val message = inputData.getString("message") ?: "Don't forget this!"
//
//        showNotification(title, message)
//        return Result.success()
//    }
//
//    private fun showNotification(title: String, message: String) {
//        val channelId = "reminder_channel"
////        val soundUri = Uri.parse("android.resource://${applicationContext.packageName}/${R.raw.notify}")
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val attributes = AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .build()
//
//            val channel = NotificationChannel(
//                channelId,
//                "Reminder Notifications",
//                NotificationManager.IMPORTANCE_HIGH
//            )
////                .apply {
////                setSound(soundUri, attributes) // ✅ THIS LINE IS CRITICAL
////            }
//
//            val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manager.createNotificationChannel(channel)
//        }
//
//        val intent = applicationContext.packageManager
//            .getLaunchIntentForPackage(applicationContext.packageName)
//        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//        val pendingIntent = PendingIntent.getActivity(
//            applicationContext,
//            0,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val builder = NotificationCompat.Builder(applicationContext, channelId)
//            .setSmallIcon(R.drawable.placeholde_image_24)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setAutoCancel(true)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//            // ❌ Do NOT use setSound() for Android 8+, it's ignored. Kept here only for below-26 fallback.
////            .setSound(soundUri)
//
//        // Android 13+ permission check
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.POST_NOTIFICATIONS)
//                == PackageManager.PERMISSION_GRANTED) {
//                NotificationManagerCompat.from(applicationContext).notify(1001, builder.build())
//            }
//        } else {
//            NotificationManagerCompat.from(applicationContext).notify(1001, builder.build())
//        }
//    }
//}


package com.d12.expirymonitor.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.d12.expirymonitor.R

/**
 * ✅ Worker responsible for showing a local notification
 * after a scheduled delay using WorkManager.
 */
class ReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Product Expiry Alert"
        val message = inputData.getString("message") ?: "One of your products has expired."

        showNotification(title, message)
        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "reminder_channel"

        // ✅ Create notification channel (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val channel = NotificationChannel(
                channelId,
                "Product Expiry Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Notifies when a product has expired"

            val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            manager.createNotificationChannel(channel)
        }

        // 🔗 Intent to open app when user taps the notification
        val intent = applicationContext.packageManager
            .getLaunchIntentForPackage(applicationContext.packageName)
            ?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.placeholde_image_24) // ✅ replace with your own icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // 🔔 Android 13+ permission check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                NotificationManagerCompat.from(applicationContext).notify(1001, builder.build())
            }
        } else {
            NotificationManagerCompat.from(applicationContext).notify(1001, builder.build())
        }
    }
}
