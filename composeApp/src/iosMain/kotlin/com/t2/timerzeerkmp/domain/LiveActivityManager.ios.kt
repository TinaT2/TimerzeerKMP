package com.t2.timerzeerkmp.domain

actual fun getLiveActivityManager(): LiveActivityManager {
    return IosLiveActivityManager.manager
}