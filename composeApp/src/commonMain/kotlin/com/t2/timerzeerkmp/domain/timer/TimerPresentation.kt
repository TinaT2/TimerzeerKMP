package com.t2.timerzeerkmp.domain.timer

import kotlin.time.Duration

data class TimerPresentation(
    val id: Long = 0,
    val title: String = "",
    val mode: TimerMode,
    val startTime: Long = 0,
    val duration: Duration,
    val isRunning: Boolean = false,
)