package com.t2.timerzeerkmp.domain

interface TimerController {
    fun start(durationInSeconds: Long)
    fun pause()
    fun resume()
    fun stop()
}

expect fun getTimerController(): TimerController
