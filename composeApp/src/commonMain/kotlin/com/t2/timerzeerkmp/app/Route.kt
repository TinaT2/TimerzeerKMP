package com.t2.timerzeerkmp.app

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object TimerGraph : Route

    @Serializable
    data object TimerPreview : Route

    @Serializable
    data object TimerList : Route

    @Serializable
    data class TimerFullScreen(val mode: String? =null, val title: String? = null, val initTime: Long? = 0L) : Route
}