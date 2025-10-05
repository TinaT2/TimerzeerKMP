package com.t2.timerzeerkmp.domain.timer

import com.t2.timerzeerkmp.app.Route


sealed interface TimerIntent {
    data class Start (val timerInit: Route.TimerFullScreen?) : TimerIntent
    data object Pause : TimerIntent
    data object Resume : TimerIntent
    data object Stop : TimerIntent
}