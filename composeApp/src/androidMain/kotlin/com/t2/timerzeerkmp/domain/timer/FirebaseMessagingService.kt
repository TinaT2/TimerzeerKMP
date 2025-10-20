package com.t2.timerzeerkmp.domain.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.t2.timerzeerkmp.MainActivity
import com.t2.timerzeerkmp.R
import com.t2.timerzeerkmp.domain.util.Log
import com.t2.timerzeerkmp.domain.util.Tags

class FirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(Tags.TOKEN, token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(remoteMessage)
    }

    fun showNotification(msg: RemoteMessage) {
        val channelId = "timer_channel"
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(
            NotificationChannel(
                channelId,
                "Timer",
                NotificationManager.IMPORTANCE_HIGH
            )
        )

        val intent = Intent(this, MainActivity::class.java)
        val p = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val contentView = RemoteViews(packageName, R.layout.notification_custom)
        contentView.setTextViewText(
            R.id.title,
            msg.notification?.title ?: "Timer"
        )
        contentView.setTextViewText(
            R.id.body,
            msg.notification?.body ?: ""
        )

        val notif = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.property_clock_stopwatch)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCustomContentView(contentView) // custom collapsed
            .setContentIntent(p)
            .build()

        nm.notify(1, notif)
    }
}