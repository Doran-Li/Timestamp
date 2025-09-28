package com.doron.timestamp.viewmodel

import app.cash.turbine.test
import com.doron.timestamp.GlobalEventBus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SubContentViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `sendTimestamp adds new timestamp to list`() = runTest {
        val bus = GlobalEventBus()
        val viewModel = SubContentViewModel(bus)

        viewModel.timestamps.test {
            // list is empty for the first time
            assertEquals(emptyList<Long>(), awaitItem())

            viewModel.sendTimestamp()

            val newList = awaitItem()
            assertEquals(1, newList.size)
        }
    }

    @Test
    fun `collects events from GlobalEventBus`() = runTest {
        val bus = GlobalEventBus()
        val viewModel = SubContentViewModel(bus)
        val testData = 24680L

        viewModel.timestamps.test {
            assertEquals(emptyList<Long>(), awaitItem())

            bus.sendEvent(testData)

            val newList = awaitItem()
            assertEquals(listOf(testData), newList)
        }
    }

    @Test
    fun `multiple events get appended to list`() = runTest {
        val bus = GlobalEventBus()
        val viewModel = SubContentViewModel(bus)
        val testData1 = 13579L
        val testData2 = 24680L

        viewModel.timestamps.test {
            assertEquals(emptyList<Long>(), awaitItem())

            bus.sendEvent(testData1)
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(listOf(testData1), awaitItem())

            bus.sendEvent(testData2)
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(listOf(testData1, testData2), awaitItem())
        }
    }
}