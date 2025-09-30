package com.t2.timerzeerkmp.data.repository

import com.t2.timerzeerkmp.app.Route
import com.t2.timerzeerkmp.domain.LiveActivityManager
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TimerRepository(
    private val persistence: TimerPersistence,
    private val liveActivityManager: LiveActivityManager
) {
    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState

    private var timerJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

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
            persistence.saveInitialMilliSeconds(timerInit.initTime)
            persistence.saveStartEpochMillis(currentTimeMillis())
            persistence.saveIsRunning(true)
        }

        _timerState.update {
            it.copy(
                isRunning = true,
                elapsedTime = timerInit.initTime,
                mode = timerInit.mode,
                title = timerInit.title
            )
        }

        startTicking()

        liveActivityManager.start(timerInit.initTime)
    }

    private fun pauseTimer() {
        timerJob?.cancel()
        _timerState.update { it.copy(isRunning = false) }
        liveActivityManager.pause()
    }

    private fun resumeTimer() {
        if (_timerState.value.isRunning) return
        _timerState.update { it.copy(isRunning = true) }
        startTicking()
        liveActivityManager.resume()
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        _timerState.update { it.copy(isRunning = false, elapsedTime = -1L) }
        liveActivityManager.stop()
    }

    private fun startTicking() {
        if (timerJob != null) return

        var elapsed = _timerState.value.elapsedTime

        timerJob = scope.launch {
            while (isActive && _timerState.value.isRunning) {
                if (_timerState.value.mode == TimerMode.STOPWATCH) {
                    elapsed += 1000
                } else {
                    elapsed -= 1000
                }

                _timerState.update { it.copy(elapsedTime = elapsed) }

                delay(1.seconds)
            }
        }
    }
}