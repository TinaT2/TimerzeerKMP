package com.t2.timerzeerkmp

import com.t2.timerzeerkmp.domain.getLiveActivityManager

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}