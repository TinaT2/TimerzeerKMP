package com.t2.timerzeerkmp.app

import com.t2.timerzeerkmp.domain.timer.TimerMode
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object TimerGraph : Route

    @Serializable
    data object TimerPreview : Route

    @Serializable
    data class TimerFullScreen(val mode: TimerMode, val title: String, val initTime: Long) : Route
}