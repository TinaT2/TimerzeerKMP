package com.t2.timerzeerkmp.domain.timer

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.countdown
import timerzeerkmp.composeapp.generated.resources.stopwatch

data class TimerState(
    val title: String = "",
    val mode: TimerMode = TimerMode.STOPWATCH,
    val initialTime: Long? = null,
    val elapsedTime: Long = -1L,
    val isRunning: Boolean = false,
    val isCountDownDone: Boolean = false,
    val errorMessage: String? = null,
    val startEpocMilliSecond:Long? = null
)
@Serializable
enum class TimerMode(val value: StringResource) {
    STOPWATCH(Res.string.stopwatch), COUNTDOWN(Res.string.countdown)
}