package com.t2.timerzeerkmp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.t2.timerzeerkmp.data.di.initKoin
import com.t2.timerzeerkmp.domain.timer.TimerService.Companion.CHANNEL_ID
import com.t2.timerzeerkmp.domain.timer.TimerService.Companion.CHANNEL_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class TimezeerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ensureNotificationChannel()
        initKoin {
            androidContext(this@TimezeerApplication)
            androidLogger(Level.DEBUG)
        }
    }

    fun Context.ensureNotificationChannel(
        channelId: String = CHANNEL_ID,
        name: String = CHANNEL_NAME,
        importance: Int = NotificationManager.IMPORTANCE_LOW
    ) {
        val manager = getSystemService(NotificationManager::class.java)
        val existing = manager.getNotificationChannel(channelId)

        if (existing == null) {
            val channel = NotificationChannel(channelId, name, importance)
            manager.createNotificationChannel(channel)
        }
    }
}

