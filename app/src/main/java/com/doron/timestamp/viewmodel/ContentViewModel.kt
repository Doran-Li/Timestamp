package com.doron.timestamp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doron.timestamp.GlobalEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val eventBus: GlobalEventBus
): ViewModel() {

    private val _timestamps = MutableStateFlow<List<Long>>(emptyList())
    val timestamps: StateFlow<List<Long>> = _timestamps

    init {
        viewModelScope.launch {
            eventBus.events.collect { ts ->
                _timestamps.update { old -> old + ts } //append to the end of the list
            }
        }
    }

    fun sendTimestamp() {
        viewModelScope.launch {
            eventBus.sendEvent(System.currentTimeMillis())
        }
    }
}
