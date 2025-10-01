package com.t2.timerzeerkmp.domain.util

expect object Log {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}