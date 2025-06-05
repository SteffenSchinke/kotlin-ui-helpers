package de.schinke.steffen.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import de.schinke.steffen.base_classs.AppNotificationChannel

class AppNotification(

    val channel: AppNotificationChannel,
    private val context: Context,
) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val hasNotificationPermission: Boolean
        get() = notificationManager.areNotificationsEnabled()


    init {

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(
            channel.id,
            channel.name,
            importance
        ).apply {
            description = "Benachrichtigungen für ${channel.name}"
        }

        if (notificationManager.getNotificationChannel(channel.id) == null) {
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


    fun showNotification(message: String, priority: Int = NotificationCompat.PRIORITY_DEFAULT) {

        if (hasNotificationPermission) {
            val appName = context.applicationInfo.loadLabel(context.packageManager).toString()

            val notification = NotificationCompat.Builder(context, channel.id)
                .setSmallIcon(channel.iconId)
                .setContentTitle(appName)
                .setContentText(message)
                .setPriority(priority)
                .build()

            notificationManager.notify(channel.id.hashCode(), notification)
        } else {

            Log.i(AppNotification::class.simpleName, "Notification permission not granted")
        }
    }
}