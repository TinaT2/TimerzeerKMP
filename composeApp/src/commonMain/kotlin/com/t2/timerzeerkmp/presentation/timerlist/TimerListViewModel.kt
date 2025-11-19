package com.t2.timerzeerkmp.presentation.timerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t2.timerzeerkmp.data.repository.TimerRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerListViewModel(
    private val timerRepository: TimerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TimerListState())
    val state = _state.asStateFlow()

    val _effect = MutableSharedFlow<TimerListEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadTimers()
    }

    fun onIntent(intent: TimerListIntent) {
        when (intent) {
            TimerListIntent.LoadTimers -> loadTimers()
            TimerListIntent.OnBack -> {
                viewModelScope.launch {
                    _effect.emit(TimerListEffect.OnBackPressed)
                }
            }
        }
    }

    private fun loadTimers() {
        viewModelScope.launch {
            timerRepository.getAllTimers().collect {
                _state.value = _state.value.copy(
                    timers = it
                )
            }
        }
    }
}