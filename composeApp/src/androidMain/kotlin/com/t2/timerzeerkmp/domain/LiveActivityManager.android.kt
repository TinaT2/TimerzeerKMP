package com.t2.timerzeerkmp.domain

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.t2.timerzeerkmp.domain.timer.TimerIntent
import com.t2.timerzeerkmp.domain.timer.TimerService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual fun getTimerController(): TimerController =
    object : TimerController, KoinComponent {
        val context: Context by inject<Context>()

        override fun start(durationInSeconds: Long) {
            val i = Intent(context, TimerService::class.java).apply {
                action = TimerIntent::Start.toString()
            }
            ContextCompat.startForegroundService(context, i)
        }

        override fun pause() {
            context.startService(Intent(context, TimerService::class.java).apply {
                action = TimerIntent.Pause.toString()
            })
        }

        override fun resume() {
            ContextCompat.startForegroundService(
                context,
                Intent(context, TimerService::class.java).apply {
                    action = TimerIntent.Resume.toString()
                })
        }

        override fun stop() {
            context.startService(Intent(context, TimerService::class.java).apply {
                action = TimerIntent.Stop.toString()
            })
        }
    }
