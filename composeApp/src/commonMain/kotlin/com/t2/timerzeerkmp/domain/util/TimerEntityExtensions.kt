package com.t2.timerzeerkmp.domain.util

import com.t2.timerzeerkmp.data.database.entity.TimerEntity

fun TimerEntity.getFormattedTitle(): String {
    return if (title.isNotEmpty()) {
        "$title:"
    } else {
        ""
    }
}
