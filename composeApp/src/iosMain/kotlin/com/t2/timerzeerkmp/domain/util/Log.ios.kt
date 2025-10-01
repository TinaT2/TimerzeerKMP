package com.t2.timerzeerkmp.domain.util

import platform.Foundation.NSLog

actual object Log {
    actual fun d(tag: String, message: String) {
        NSLog("DEBUG: %s - %s", tag, message)
    }

    actual fun e(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            NSLog("ERROR: %s - %s\nThrowable: %s", tag, message, throwable.message ?: "unknown")
        } else {
            NSLog("ERROR: %s - %s", tag, message)
        }
    }
}