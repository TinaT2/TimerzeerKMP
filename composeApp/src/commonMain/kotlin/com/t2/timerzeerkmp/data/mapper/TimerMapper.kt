package com.t2.timerzeerkmp.data.mapper

import com.t2.timerzeerkmp.data.database.TimerEntity
import com.t2.timerzeerkmp.domain.timer.TimerState
import kotlin.time.Duration.Companion.milliseconds

fun TimerState.toTimerEntity(): TimerEntity {
    return TimerEntity(
        title = title,
        mode = mode,
        startTime = startEpocMilliSecond ?: 0L,
        duration = (initialTime ?: 0L).milliseconds,
        isRunning = isRunning
    )
}
