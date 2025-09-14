package com.t2.timerzeerkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform