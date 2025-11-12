package com.t2.timerzeerkmp.data.mapper

import com.t2.timerzeerkmp.data.database.entity.TimerEntity
import com.t2.timerzeerkmp.domain.timer.TimerPresentation
import com.t2.timerzeerkmp.domain.timer.TimerState
import kotlin.time.Duration.Companion.milliseconds

fun TimerState.toTimerEntity(): TimerEntity {
    return TimerEntity(
        title = title,
        mode = mode,
        startTime = startEpocMilliSecond ?: 0L,
        duration = elapsedTime.milliseconds,
        isRunning = isRunning
    )
}

fun TimerEntity.toTimerPresenter() = TimerPresentation(
    id = id,
    title = title,
    mode = mode,
    isRunning = isRunning,
    startTime = startTime,
    duration = duration
)
