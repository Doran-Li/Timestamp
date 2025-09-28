package com.doron.timestamp

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalEventBus @Inject constructor() {
    private val _events = MutableSharedFlow<Long>(replay = 1)
    val events = _events.asSharedFlow()

    suspend fun sendEvent(timestamp: Long) {
        _events.emit(timestamp)
    }
}
