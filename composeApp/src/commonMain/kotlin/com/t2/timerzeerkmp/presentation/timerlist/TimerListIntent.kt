package com.t2.timerzeerkmp.presentation.timerlist

sealed interface TimerListIntent {
    data object LoadTimers : TimerListIntent
}