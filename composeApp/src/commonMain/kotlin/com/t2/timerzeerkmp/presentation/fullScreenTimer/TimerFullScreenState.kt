package com.t2.timerzeerkmp.presentation.fullScreenTimer

import com.t2.timerzeerkmp.domain.error.TimerZeerError
import com.t2.timerzeerkmp.domain.timer.TimerMode
import com.t2.timerzeerkmp.domain.timer.TimerState

data class TimerUiState(
    val hide: Boolean = false,
    val lock: Boolean = false,
    val iconAppear: Boolean = false
)

data class FullScreenTimerState(
    val timer: TimerState,
    val ui: TimerUiState
)