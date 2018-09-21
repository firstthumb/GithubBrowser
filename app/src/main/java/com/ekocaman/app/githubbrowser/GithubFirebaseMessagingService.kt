package com.ekocaman.app.githubbrowser

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.ekocaman.app.githubbrowser.domain.job.FirebaseJobService
import com.ekocaman.app.githubbrowser.domain.service.FirebaseService
import com.ekocaman.app.githubbrowser.ui.main.MainActivity
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject


class GithubFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    internal lateinit var firebaseService: FirebaseService

    @Inject
    internal lateinit var dispatcher: FirebaseJobDispatcher

    override fun onCreate() {
        super.onCreate()

        AndroidInjection.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Timber.v("From: ${remoteMessage?.from}")

        remoteMessage?.data?.isNotEmpty()?.let {
            Timber.v("Message data payload: ${remoteMessage.data}")

            if (true) {
                scheduleJob()
            } else {
                handleNow()
            }
        }

        remoteMessage?.notification?.let {
            Timber.v("Message Notification Body: ${it.body}")
            it.body?.let {
                sendNotification(it)
            }
        }
    }

    override fun onNewToken(token: String?) {
        Timber.v("Refreshed token: $token")

        sendRegistrationToServer(token)
    }

    private fun scheduleJob() {
        val myJob = dispatcher.newJobBuilder()
                .setService(FirebaseJobService::class.java)
                .setTag("firebase-job")
                .build()
        dispatcher.schedule(myJob)
    }

    private fun handleNow() {
        Timber.v("Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        Timber.v("FCM Token : $token")
        firebaseService.saveFirebaseMessagingToken(token)
    }

    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */,
                intent,
                PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_stars)
                .setContentTitle(getString(R.string.fcm_message))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    getString(R.string.default_notification_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}