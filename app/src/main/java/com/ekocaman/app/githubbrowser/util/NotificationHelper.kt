package com.ekocaman.app.githubbrowser.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import com.ekocaman.app.githubbrowser.R
import javax.inject.Inject

internal class NotificationHelper @Inject constructor(val context: Context): ContextWrapper(context) {
    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan1 = NotificationChannel("", getString(R.string.default_notification_channel_id), NotificationManager.IMPORTANCE_DEFAULT)
            chan1.lightColor = Color.GREEN
            chan1.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            manager.createNotificationChannel(chan1)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}