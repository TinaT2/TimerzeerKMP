package com.t2.timerzeerkmp.data.repository

import com.t2.timerzeerkmp.domain.persistence.TimerPersistence
import com.t2.timerzeerkmp.domain.timer.TimerMode
import com.t2.timerzeerkmp.domain.timer.TimerState
import com.t2.timerzeerkmp.domain.util.currentTimeMillis
import com.t2.timerzeerkmp.presentation.fullScreenTimer.TimerIntent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TimerRepository(
    private val persistence: TimerPersistence
) {
    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState

    suspend fun startTimer(initial: Long) {
        persistence.saveInitialSeconds(initial)
        persistence.saveStartEpochMillis(currentTimeMillis())
        persistence.saveIsRunning(true)
    }

    suspend fun restoreTimer(): Long? {
        val start = persistence.getStartEpochMillis() ?: return null
        val initial = persistence.getInitialSeconds() ?: return null
        return initial - ((currentTimeMillis() - start) / 1000)
    }
    init {
        startService()
    }



    fun startService() {
//        val intent = Intent(application, TimerService::class.java)
//        ContextCompat.startForegroundService(application, intent)
    }

    fun update(seconds: Long) {
        if (timerState.value.mode == TimerMode.COUNTDOWN && seconds == 0L) {
            _timerState.update { it.copy(elapsedTime = seconds, isCountDownDone = true) }
            onTimerIntent(TimerIntent.Stop)
        } else
            _timerState.update { it.copy(elapsedTime = seconds) }
    }

    fun update(mode: TimerMode, title: String, initialTime: Long?) {
        _timerState.update {
            it.copy(mode = mode, title = title, initialTime = initialTime)
        }
    }

    fun reset() {
        _timerState.update { it.copy(elapsedTime = 0L) }
    }


    fun onTimerIntent(intent: TimerIntent?) {
        when (intent) {
            TimerIntent.Start -> {
                if (_timerState.value.isRunning) return
                _timerState.update {
                    it.copy(
                        isRunning = true,
                        elapsedTime = if (_timerState.value.mode == TimerMode.COUNTDOWN) _timerState.value.initialTime
                            ?: 0L else 0L,
                        isCountDownDone = false
                    )
                }
//todo                val intent = Intent(application, TimerService::class.java)
//                intent.apply {
//                    action = TimerForegroundActions.ACTION_START.name
//                }
//
//                application.startService(intent)
            }

            TimerIntent.Pause -> {
//                val intent = Intent(application, TimerService::class.java).apply {
//                    action = TimerForegroundActions.ACTION_PAUSE.name
//                }
//                application.startService(intent)
                _timerState.update { it.copy(isRunning = false) }
            }

            TimerIntent.Resume -> {
                if (!_timerState.value.isRunning) {
                    _timerState.update { it.copy(isRunning = true) }
//                    val intent = Intent(application, TimerService::class.java).apply {
//                        action = TimerForegroundActions.ACTION_RESUME.name
//                    }
//                    ContextCompat.startForegroundService(application, intent)
                }
            }

            TimerIntent.Stop -> {
//                val intent = Intent(application, TimerService::class.java).apply {
//                    action = TimerForegroundActions.ACTION_STOP.name
//                }
//                application.startService(intent)
                _timerState.update { it.copy(isRunning = false) }
            }

            null -> {}
        }
    }
}