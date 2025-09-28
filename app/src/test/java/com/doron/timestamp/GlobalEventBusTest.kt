package com.doron.timestamp

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import kotlin.test.Test

class GlobalEventBusTest {

    @Test
    fun `sendEvent emits to events flow`() = runTest {
        val bus = GlobalEventBus()
        val testData = 135790L

        bus.events.test {
            bus.sendEvent(testData)

            assertEquals(testData, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `new subscriber receives last replayed event`() = runTest {
        val bus = GlobalEventBus()
        val testData = 2468L
        bus.sendEvent(testData)

        bus.events.test {
            assertEquals(testData, awaitItem())
        }
    }
}