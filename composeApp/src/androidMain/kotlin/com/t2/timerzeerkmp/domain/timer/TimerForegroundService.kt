package com.t2.timerzeerkmp.domain.timer

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.t2.timerzeerkmp.MainActivity
import com.t2.timerzeerkmp.R
import com.t2.timerzeerkmp.data.mapper.toDisplayString
import com.t2.timerzeerkmp.data.mapper.toTimeComponents
import com.t2.timerzeerkmp.data.repository.TimerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class TimerService : LifecycleService() {

    private val timerRepository: TimerRepository by inject()
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var timerJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_ID, buildNotification(0L))
        observeTimerState()
    }

    private fun observeTimerState() {
        timerJob = serviceScope.launch {
            timerRepository.timerState.collect { state ->
                // Update notification for every tick
                val elapsed = state.elapsedTime
                val notification = buildNotification(elapsed)
                val manager = getSystemService(NotificationManager::class.java)
                manager.notify(NOTIFICATION_ID, notification)
            }
        }
    }

    private fun buildNotification(elapsed: Long): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val contentText = "${timerRepository.timerState.value.mode.name.lowercase()}: ${
            elapsed.toTimeComponents().toDisplayString()
        }"

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.no_timer_running))
            .setContentText(contentText)
            .setSmallIcon(R.drawable.property_clock_stopwatch)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        timerJob?.cancel()
    }

    companion object {
        private const val TAG = "TimerService"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "timer_channel"
        const val CHANNEL_NAME = "Timer"
    }
}