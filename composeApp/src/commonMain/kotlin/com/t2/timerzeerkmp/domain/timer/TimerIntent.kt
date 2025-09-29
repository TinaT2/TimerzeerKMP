package com.t2.timerzeerkmp.domain.timer


sealed interface TimerIntent {
    data class Start (val initialMilliSeconds: Long) : TimerIntent
    data object Pause : TimerIntent
    data object Resume : TimerIntent
    data object Stop : TimerIntent
}