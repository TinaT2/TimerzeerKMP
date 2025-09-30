package com.t2.timerzeerkmp.presentation.timerPreview

import com.t2.timerzeerkmp.domain.error.TimerZeerError
import com.t2.timerzeerkmp.domain.timer.TimerMode
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.countdown_title
import timerzeerkmp.composeapp.generated.resources.stopwatch_title


data class TimerPreviewState(
    private val stopwatchTitle: String = "",
    private val countdownTitle: String = "",
    val countDownInitTime: Long = 0L,
    val mode: TimerMode = TimerMode.STOPWATCH,
    val errorMessage: TimerZeerError? = null
) {
    fun getTitle() = if (mode == TimerMode.STOPWATCH) stopwatchTitle else countdownTitle

    fun getPlaceHolder() =
        if (mode == TimerMode.STOPWATCH) Res.string.stopwatch_title else Res.string.countdown_title

    fun getOnTitleChange(title: String) =
        if (mode == TimerMode.STOPWATCH) TimerPreviewIntent.OnStopwatchTitleChange(title) else TimerPreviewIntent.OnCountDownTitleChange(
            title
        )

    fun getInitTime() = if (mode == TimerMode.STOPWATCH) 0L else countDownInitTime

}