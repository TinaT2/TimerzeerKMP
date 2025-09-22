package com.t2.timerzeerkmp.domain.error

interface TimerZeerError {
    val message: String
}

object UnknownError: TimerZeerError{
    override val message: String
        get() = "Something went wrong"
}