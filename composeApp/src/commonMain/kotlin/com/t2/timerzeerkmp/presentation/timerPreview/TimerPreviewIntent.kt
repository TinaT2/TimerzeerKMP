package com.t2.timerzeerkmp.presentation.timerPreview

import com.t2.timerzeerkmp.domain.timer.TimerMode
import org.jetbrains.compose.resources.StringResource


sealed interface TimerPreviewIntent {
    data class OnStopwatchTitleChange(val name: String) : TimerPreviewIntent
    data class OnCountDownTitleChange(val name: String) : TimerPreviewIntent
    data class OnModeChange(val mode: TimerMode) : TimerPreviewIntent
    data object OnSecondIncrease : TimerPreviewIntent
    data object OnSecondDecrease : TimerPreviewIntent
    data object OnMinutesIncrease : TimerPreviewIntent
    data object OnMinutesDecrease : TimerPreviewIntent
    data object OnHourIncrease : TimerPreviewIntent
    data object OnHourDecrease : TimerPreviewIntent
    data object OnDayIncrease : TimerPreviewIntent
    data object OnDayDecrease : TimerPreviewIntent
    data class SetDate(val countDownInitTimer: Long) : TimerPreviewIntent
    data class SetEndingAnimation(val endingAnimation: StringResource): TimerPreviewIntent
    data class SetBackground(val backgroundId: StringResource): TimerPreviewIntent
    data class SetStyle(val styleId: StringResource): TimerPreviewIntent
    data object OnTimerStarted: TimerPreviewIntent
}

sealed interface UiOverlayIntent {
    data object None : UiOverlayIntent
    data object TimerStyle : UiOverlayIntent
    data object BackgroundTheme : UiOverlayIntent
    data object EndingAnimation : UiOverlayIntent
    data object DatePicker : UiOverlayIntent
}
