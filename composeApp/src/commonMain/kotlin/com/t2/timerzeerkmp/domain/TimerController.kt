package com.t2.timerzeerkmp.domain

interface TimerController {
    fun start(durationInSeconds: Long)
    fun pause()
    fun resume(durationInSeconds: Long)
    fun stop()
}

expect fun getTimerController(): TimerController
