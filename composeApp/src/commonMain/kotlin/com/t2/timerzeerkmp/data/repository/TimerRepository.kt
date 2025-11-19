package com.t2.timerzeerkmp.data.repository

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.t2.timerzeerkmp.app.Route
import com.t2.timerzeerkmp.data.database.dao.TimerDao
import com.t2.timerzeerkmp.data.mapper.toTimerEntity
import com.t2.timerzeerkmp.data.mapper.toTimerPresenter
import com.t2.timerzeerkmp.domain.TimerController
import com.t2.timerzeerkmp.domain.persistence.TimerPersistence
import com.t2.timerzeerkmp.domain.timer.TimerIntent
import com.t2.timerzeerkmp.domain.timer.TimerMode
import com.t2.timerzeerkmp.domain.timer.TimerState
import com.t2.timerzeerkmp.domain.util.Log
import com.t2.timerzeerkmp.domain.util.currentTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TimerRepository(
    private val persistence: TimerPersistence,
    private val timerController: TimerController,
    private val timerDao: TimerDao
) {
    private val _timerState = MutableStateFlow(TimerState())

    @NativeCoroutinesState
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> get() = _isReady

    private var timerJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val TAG = "TimerRepository"

    init {
        scope.launch {
            timerJob?.cancel()
            _timerState.update {
                it.copy(
                    isRunning = persistence.getIsRunning().first() ?: false,
                    elapsedTime = persistence.getElapsedTime().first() ?: 0,
                    mode = TimerMode.valueOf(persistence.getMode().first()),
                    title = persistence.getTitle().first(),
                    startEpocMilliSecond = persistence.getStartEpochMillis().first() ?: 0L,
                    initialTime = persistence.getInitialMilliSeconds().first()
                )
            }
            Log.d(TAG, "init,isRunning:${_timerState.value.isRunning}")
            _isReady.value = true
            if (timerState.value.isRunning) startTicking()
        }

        scope.launch {
            timerState.collect { state ->
                persistence.saveIsRunning(state.isRunning)
                state.mode.name.let { persistence.saveMode(it) }
                persistence.saveTitle(state.title)
                persistence.saveElapsedTime(state.elapsedTime)
                state.initialTime?.let { persistence.saveInitialMilliSeconds(it) }
                state.startEpocMilliSecond?.let { persistence.saveStartEpochMillis(it) }
            }
        }
    }

    suspend fun getIsRunning() = persistence.getIsRunning()

    fun onTimerIntent(intent: TimerIntent?) {
        when (intent) {
            is TimerIntent.Start -> {
                Log.d(TAG, "onTimerIntent Start: ${_timerState.value.isRunning}")
                if (_timerState.value.isRunning) return
                startTimer(intent.timerInit)
            }

            TimerIntent.Pause -> pauseTimer()
            TimerIntent.Resume -> resumeTimer()
            TimerIntent.Stop -> {
                insertTimer(timerState.value.copy(isRunning = false))
                stopTimer()
            }

            else -> {}
        }
    }

    private fun startTimer(timerInit: Route.TimerFullScreen?) {
        scope.launch {
            delay(1.seconds)
            if (timerInit?.mode != null && timerInit.title != null && timerInit.initTime != null) {
                val mode = TimerMode.valueOf(timerInit.mode)
                _timerState.update {
                    it.copy(
                        mode = mode,
                        title = timerInit.title,
                        initialTime = timerInit.initTime,
                        isRunning = true,
                        elapsedTime = timerInit.initTime,
                        isCountDownDone = false,
                        startEpocMilliSecond = currentTimeMillis()
                    )
                }
            }
            startTicking()
            timerController.start(timerState.value.initialTime ?: 0L)
        }
    }

    private fun pauseTimer() {
        timerJob?.cancel()
        _timerState.update { it.copy(isRunning = false) }
        timerController.pause()
    }

    private fun resumeTimer() {
        if (_timerState.value.isRunning) return

        scope.launch {
            val lastElapsed = persistence.getElapsedTime().first() ?: 0L
            Log.d(TAG, "resumeTimer: $lastElapsed")
            val now = currentTimeMillis()
            _timerState.update {
                it.copy(
                    isRunning = true,
                    startEpocMilliSecond = now,
                    initialTime = lastElapsed
                )
            }
            startTicking()
            timerController.resume(if (timerState.value.mode == TimerMode.STOPWATCH) -lastElapsed else lastElapsed)
        }

    }

    private fun stopTimer() {
        _timerState.update { it.copy(isRunning = false, elapsedTime = -1L, initialTime = 0L) }
        timerJob?.cancel()
        timerJob = null
        timerController.stop()
    }

    private fun startTicking() {
        timerJob = scope.launch {
            if (isReady.value) {
                val start = timerState.value.startEpocMilliSecond ?: currentTimeMillis()
                val initialMillis = timerState.value.initialTime ?: 0L
                Log.d(TAG, "isRunning:" + _timerState.value.isRunning.toString())
                while (_timerState.value.isRunning) {
                    val elapsed = if (_timerState.value.mode == TimerMode.STOPWATCH) {
                        initialMillis + (currentTimeMillis() - start)
                    } else {
                        start + initialMillis - currentTimeMillis()
                    }
                    Log.d(TAG, "timer:$elapsed")
                    _timerState.update { it.copy(elapsedTime = elapsed) }

                    if (elapsed < 0L && timerState.value.mode == TimerMode.COUNTDOWN) {
                        Log.d(TAG, "countdownDone")
                        _timerState.update { it.copy(isCountDownDone = true) }
                        stopTimer()
                    }

                    delay(1.seconds)
                }
            }
        }
    }

    private fun insertTimer(timerState: TimerState) {
        scope.launch {
            timerDao.insert(timerState.toTimerEntity())
        }
    }

    fun getAllTimers() =
        timerDao.getAllTimers().map { it.map { it.toTimerPresenter() } }

}
