package com.t2.timerzeerkmp.data.repository

import com.t2.timerzeerkmp.app.Route
import com.t2.timerzeerkmp.domain.TimerController
import com.t2.timerzeerkmp.domain.persistence.TimerPersistence
import com.t2.timerzeerkmp.domain.timer.TimerIntent
import com.t2.timerzeerkmp.domain.timer.TimerMode
import com.t2.timerzeerkmp.domain.timer.TimerState
import com.t2.timerzeerkmp.domain.util.currentTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TimerRepository(
    private val persistence: TimerPersistence,
    private val timerController: TimerController
) {
    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState

    private var timerJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        scope.launch {
            if (persistence.getIsRunning().first() == true) {
                timerJob?.cancel()
                _timerState.update {
                    it.copy(
                        isRunning = true,
                        elapsedTime = persistence.getInitialMilliSeconds().first() ?: 0,
                        mode = TimerMode.valueOf(persistence.getMode().first()),
                        title = persistence.getTitle().first()
                    )
                }
                startTicking()
            }
        }
    }

    fun onTimerIntent(intent: TimerIntent?) {
        when (intent) {
            is TimerIntent.Start -> {
                startTimer(intent.timerInit)
            }

            TimerIntent.Pause -> pauseTimer()
            TimerIntent.Resume -> resumeTimer()
            TimerIntent.Stop -> stopTimer()
            else -> {}
        }
    }

    private fun startTimer(timerInit: Route.TimerFullScreen) {
        scope.launch {
            delay(1.seconds)
            persistence.saveInitialMilliSeconds(timerInit.initTime)
            persistence.saveStartEpochMillis(currentTimeMillis())
            persistence.saveIsRunning(true)
            persistence.saveMode(timerInit.mode)
            persistence.saveTitle(timerInit.title)
            startTicking()
            timerController.start(timerInit.initTime)
        }

        _timerState.update {
            it.copy(
                isRunning = true,
                elapsedTime = timerInit.initTime,
                mode = TimerMode.valueOf(timerInit.mode),
                title = timerInit.title,
                isCountDownDone = false
            )
        }
    }

    private fun pauseTimer() {
        timerJob?.cancel()
        _timerState.update { it.copy(isRunning = false) }
        timerController.pause()
    }

    private fun resumeTimer() {
        if (_timerState.value.isRunning) return

        _timerState.update { it.copy(isRunning = true) }

        scope.launch {
            // Get last known state
            val lastElapsed = persistence.getElapsedTime().first() ?: 0L
            val now = currentTimeMillis()

            // Save resume reference point
            persistence.saveStartEpochMillis(now)
            persistence.saveIsRunning(true)
            persistence.saveInitialMilliSeconds(lastElapsed)

            // Restart ticking
            startTicking()
            timerController.resume()
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        _timerState.update { it.copy(isRunning = false, elapsedTime = -1L) }
        timerController.stop()
    }

    private fun startTicking() {
        scope.launch {
            persistence.saveStartEpochMillis(currentTimeMillis())

            while (_timerState.value.isRunning) {
                val start = persistence.getStartEpochMillis().first() ?: currentTimeMillis()
                val initialMillis = persistence.getInitialMilliSeconds().first() ?: 0L
                val elapsed = if (_timerState.value.mode == TimerMode.STOPWATCH) {
                    initialMillis + (currentTimeMillis() - start)
                } else {
                    initialMillis - (currentTimeMillis() - start)
                }

                persistence.saveElapsedTime(elapsed)
                _timerState.update { it.copy(elapsedTime = elapsed) }
                delay(1.seconds)
            }
        }
    }
}