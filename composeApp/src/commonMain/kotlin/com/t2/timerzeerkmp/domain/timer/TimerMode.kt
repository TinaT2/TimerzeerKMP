package com.t2.timerzeerkmp.domain.timer

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import timerzeerkmp.composeapp.generated.resources.Res
import timerzeerkmp.composeapp.generated.resources.countdown
import timerzeerkmp.composeapp.generated.resources.stopwatch

@Serializable
enum class TimerMode(val value: StringResource) {
    STOPWATCH(Res.string.stopwatch), COUNTDOWN(Res.string.countdown)
}