package com.t2.timerzeerkmp.presentation.timerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t2.timerzeerkmp.data.database.dao.TimerDao
import com.t2.timerzeerkmp.data.repository.TimerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TimerListViewModel(
    private val timerRepository: TimerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TimerListState())
    val state = _state.asStateFlow()

    init {
        loadTimers()
    }

    fun onIntent(intent: TimerListIntent) {
        when (intent) {
            TimerListIntent.LoadTimers -> loadTimers()
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