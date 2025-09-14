package com.t2.timerzeerkmp.domain


actual fun getLiveActivityManager(): LiveActivityManager {
    // Return your Android foreground service implementation
    return object : LiveActivityManager { /* ... */
        override fun start(durationInSeconds: Long) {

        }

        override fun stop() {
        }

    }
}