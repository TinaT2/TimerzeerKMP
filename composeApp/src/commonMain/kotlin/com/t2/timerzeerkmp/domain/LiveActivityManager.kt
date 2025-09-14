package com.t2.timerzeerkmp.domain

interface LiveActivityManager {
    fun start(durationInSeconds: Long)
    fun stop()
}

expect fun getLiveActivityManager(): LiveActivityManager
