
package com.t2.timerzeerkmp.data.database

import androidx.room.TypeConverter
import com.t2.timerzeerkmp.domain.timer.TimerMode
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class TimerTypeConvertor {
    @TypeConverter
    fun fromTimerMode(mode: TimerMode): String {
        return mode.name
    }

    @TypeConverter
    fun toTimerMode(name: String): TimerMode {
        return TimerMode.valueOf(name)
    }

    @TypeConverter
    fun fromDuration(duration: Duration): Long {
        return duration.inWholeMilliseconds
    }

    @TypeConverter
    fun toDuration(milliseconds: Long): Duration {
        return milliseconds.milliseconds
    }
}
