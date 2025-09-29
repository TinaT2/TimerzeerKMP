package com.t2.timerzeerkmp.presentation.fullScreenTimer

import com.t2.timerzeerkmp.domain.timer.TimerIntent

sealed interface TimerFullScreenIntent {
    data class Start(val initialMilliSeconds: Long) : TimerFullScreenIntent
    data object Pause : TimerFullScreenIntent
    data object Resume : TimerFullScreenIntent
    data object Stop : TimerFullScreenIntent
    data object Hide : TimerFullScreenIntent
    data object Lock : TimerFullScreenIntent
    data object IconAppear : TimerFullScreenIntent
}


fun TimerFullScreenIntent.toTimerIntent(): TimerIntent? = when (this) {
    is TimerFullScreenIntent.Start -> {
        val initialMs = this.initialMilliSeconds
        TimerIntent.Start(initialMs)
    }
    TimerFullScreenIntent.Pause -> TimerIntent.Pause
    TimerFullScreenIntent.Resume -> TimerIntent.Resume
    TimerFullScreenIntent.Stop -> TimerIntent.Stop
    else -> null
}