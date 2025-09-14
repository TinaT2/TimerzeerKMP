package com.t2.timerzeerkmp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.tina.timerzeer.di.initKoin
import com.tina.timerzeer.di.timerModule
import com.tina.timerzeer.timer.presentation.fullScreenTimer.TimerService.Companion.CHANNEL_ID
import com.tina.timerzeer.timer.presentation.fullScreenTimer.TimerService.Companion.CHANNEL_NAME
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
            modules(timerModule)
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

