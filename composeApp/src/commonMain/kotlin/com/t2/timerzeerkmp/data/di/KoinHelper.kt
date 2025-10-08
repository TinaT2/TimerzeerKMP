package com.t2.timerzeerkmp.data.di

import com.t2.timerzeerkmp.data.repository.TimerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object KoinHelper : KoinComponent {
    val timerRepository: TimerRepository by inject()
}

fun getTimerRepository(): TimerRepository = KoinHelper.timerRepository