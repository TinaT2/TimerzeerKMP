package com.t2.timerzeerkmp.presentation.timerPreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t2.timerzeerkmp.data.mapper.minusDay
import com.t2.timerzeerkmp.data.mapper.minusHour
import com.t2.timerzeerkmp.data.mapper.minusMinute
import com.t2.timerzeerkmp.data.mapper.minusSecond
import com.t2.timerzeerkmp.data.mapper.plusDay
import com.t2.timerzeerkmp.data.mapper.plusHour
import com.t2.timerzeerkmp.data.mapper.plusMinute
import com.t2.timerzeerkmp.data.mapper.plusSecond
import com.t2.timerzeerkmp.data.repository.SettingsRepository
import com.t2.timerzeerkmp.data.repository.TimerRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TimerPreviewViewModel(
    private val settingsRepository: SettingsRepository,
    private val timerRepository: TimerRepository
) : ViewModel() {

    private val _timerPreviewState = MutableStateFlow(TimerPreviewState())
    val timerPreviewState: StateFlow<TimerPreviewState> =
        _timerPreviewState
    private var timerJob: Job? = null

    fun onUserAction(action: TimerPreviewIntent) {
        when (action) {
            is TimerPreviewIntent.OnStopwatchTitleChange -> {
                _timerPreviewState.update {
                    it.copy(stopwatchTitle = action.name)
                }
            }

            is TimerPreviewIntent.OnModeChange -> {
                _timerPreviewState.update {
                    if (it.mode != action.mode) {
                        it.copy(mode = action.mode)
                    } else it
                }
            }

            is TimerPreviewIntent.OnCountDownTitleChange -> {
                _timerPreviewState.update {
                    it.copy(countdownTitle = action.name)
                }
            }

            TimerPreviewIntent.OnHourDecrease -> {
                _timerPreviewState.update { it.copy(countDownInitTime = it.countDownInitTime.minusHour()) }
            }

            TimerPreviewIntent.OnHourIncrease -> {
                _timerPreviewState.update { it.copy(countDownInitTime = it.countDownInitTime.plusHour()) }
            }

            TimerPreviewIntent.OnMinutesDecrease -> {
                _timerPreviewState.update { it.copy(countDownInitTime = it.countDownInitTime.minusMinute()) }
            }

            TimerPreviewIntent.OnMinutesIncrease -> {
                _timerPreviewState.update { it.copy(countDownInitTime = it.countDownInitTime.plusMinute()) }
            }

            TimerPreviewIntent.OnSecondDecrease -> {
                _timerPreviewState.update { it.copy(countDownInitTime = it.countDownInitTime.minusSecond()) }
            }

            TimerPreviewIntent.OnSecondIncrease -> {
                _timerPreviewState.update { it.copy(countDownInitTime = it.countDownInitTime.plusSecond()) }
            }

            TimerPreviewIntent.OnDayDecrease -> {
                _timerPreviewState.update { it.copy(countDownInitTime = it.countDownInitTime.minusDay()) }
            }

            TimerPreviewIntent.OnDayIncrease -> {
                _timerPreviewState.update { it.copy(countDownInitTime = it.countDownInitTime.plusDay()) }
            }

            is TimerPreviewIntent.SetDate -> {
                _timerPreviewState.update { it.copy(countDownInitTime = action.countDownInitTimer) }
            }

            is TimerPreviewIntent.SetEndingAnimation -> {
                viewModelScope.launch {
                    settingsRepository.saveEndingAnimation(action.endingAnimation)
                }
            }

            is TimerPreviewIntent.SetBackground -> {
                viewModelScope.launch {
                    settingsRepository.saveBackgroundTheme(action.backgroundId)
                }
            }

            is TimerPreviewIntent.SetStyle -> {
                viewModelScope.launch {
                    settingsRepository.saveFontStyle(action.styleId)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

}
