package com.t2.timerzeerkmp.presentation.timerlist

import com.t2.timerzeerkmp.domain.timer.TimerPresentation

data class TimerListState(
    val timers: List<TimerPresentation> = emptyList()
)

sealed interface TimerListEffect{
    data object OnBackPressed: TimerListEffect
}