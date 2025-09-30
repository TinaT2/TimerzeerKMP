package com.t2.timerzeerkmp.domain

actual fun getTimerController(): TimerController {
    return IosLiveActivityManager.manager
}