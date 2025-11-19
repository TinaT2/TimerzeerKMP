package com.t2.timerzeerkmp.domain.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
fun Long.startOfDayInMillis(): Long {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val startOfDayInstant = localDateTime.date.atStartOfDayIn(TimeZone.currentSystemDefault())
    return startOfDayInstant.toEpochMilliseconds()
}
