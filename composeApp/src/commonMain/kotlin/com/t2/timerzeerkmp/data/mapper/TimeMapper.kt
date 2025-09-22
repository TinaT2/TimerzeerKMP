package com.t2.timerzeerkmp.data.mapper

import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

data class TimeComponents(
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
)

fun Long.toTimeComponents(): TimeComponents {
    val totalSeconds = this / 1000

    val days = totalSeconds / (60 * 60 * 24)
    val hours = (totalSeconds / 3600) % 24
    val minutes = ((totalSeconds / 60) % 60)
    val seconds = (totalSeconds % 60)

    return TimeComponents(days, hours, minutes, seconds)
}

fun TimeComponents.toDisplayString(): String {
    return buildString {
        if (days > 0) append("$days day${if (days > 1) "s" else ""}, ")
        if (hours > 0) append("$hours hour${if (hours > 1) "s" else ""}, ")
        if (minutes > 0) append("$minutes minute${if (minutes > 1) "s" else ""}, ")
        append("$seconds second${if (seconds > 1) "s" else ""}")
    }.trim().trimEnd(',')
}


fun Long.plusDay(): Long {
    return this + 1.days.inWholeMicroseconds
}
fun Long.minusDay(): Long {
    return (this - 1.days.inWholeMilliseconds).coerceAtLeast(0)
}

fun Long.plusHour(): Long {
    return this + 1.hours.inWholeMilliseconds
}

fun Long.minusHour(): Long {
    return (this - 1.hours.inWholeMilliseconds).coerceAtLeast(0)
}

fun Long.plusMinute(): Long {
    return this + 1.minutes.inWholeMilliseconds
}

fun Long.minusMinute(): Long {
    return (this - 1.minutes.inWholeMilliseconds).coerceAtLeast(0)
}

fun Long.plusSecond(): Long {
    return this + 1.seconds.inWholeMilliseconds
}

fun Long.minusSecond(): Long {
    return (this - 1.seconds.inWholeMilliseconds).coerceAtLeast(0)
}


