package com.t2.timerzeerkmp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.t2.timerzeerkmp.domain.timer.TimerMode
import kotlin.time.Duration

@Entity
data class TimerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String = "",
    val mode: TimerMode = TimerMode.STOPWATCH,
    val startTime: Long = 0,
    val endTime: Long? = null,
    val duration: Duration,
    val isRunning: Boolean = false,
)