package com.t2.timerzeerkmp.presentation.fullScreenTimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t2.timerzeerkmp.data.repository.TimerRepository
import com.t2.timerzeerkmp.domain.timer.TimerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class FullScreenTimerViewModel(
    private val repository: TimerRepository
) : ViewModel() {

    companion object {
        const val COUNTDOWN_DONE_DELAY_MS = 3000L
    }

    var appearJob: Job? = null
    private val _uiState = MutableStateFlow(TimerUiState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val fullState: StateFlow<FullScreenTimerState> =
        repository.isReady
            .filter { it } // Wait until repository restored
            .flatMapLatest {
                combine(repository.timerState, _uiState) { timer, ui ->
                    FullScreenTimerState(timer, ui)
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                initialValue = FullScreenTimerState(
                    TimerState(isRunning = false), // safe fallback
                    TimerUiState()
                )
            )

    fun onTimerIntent(intent: TimerFullScreenIntent) {
        when (intent) {
            is TimerFullScreenIntent.Start,
            TimerFullScreenIntent.Pause,
            TimerFullScreenIntent.Resume,
            TimerFullScreenIntent.Stop -> {
                repository.onTimerIntent(intent.toTimerIntent())
            }

            TimerFullScreenIntent.Hide -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(hide = !_uiState.value.hide) }
                    if (_uiState.value.hide) {
                        delay(3000)
                        _uiState.update { it.copy(iconAppear = false) }
                    }
                }
            }

            TimerFullScreenIntent.Lock -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(lock = !_uiState.value.lock) }
                    if (_uiState.value.lock) {
                        delay(3000)
                        _uiState.update { it.copy(iconAppear = false) }
                    }
                }
            }

            TimerFullScreenIntent.IconAppear -> {
                appearJob?.cancel()
                _uiState.update { it.copy(iconAppear = true) }
                appearJob = viewModelScope.launch {
                    delay(4000)
                    _uiState.update { it.copy(iconAppear = false) }
                }
            }
        }
    }

}
