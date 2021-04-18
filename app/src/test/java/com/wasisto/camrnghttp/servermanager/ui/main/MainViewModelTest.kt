/*
 * Copyright 2021 Andika Wasisto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wasisto.camrnghttp.servermanager.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wasisto.camrnghttp.MainCoroutineRule
import com.wasisto.camrnghttp.R
import com.wasisto.camrnghttp.servermanager.domain.usecases.GetServerIpAddressUseCase
import com.wasisto.camrnghttp.servermanager.domain.usecases.StartServerUseCase
import com.wasisto.camrnghttp.servermanager.domain.usecases.StopServerUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @MockK
    private lateinit var startServerUseCase: StartServerUseCase

    @MockK
    private lateinit var stopServerUseCase: StopServerUseCase

    @MockK
    private lateinit var getServerIpAddressUseCase: GetServerIpAddressUseCase

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mainViewModel = MainViewModel(
            startServerUseCase,
            stopServerUseCase,
            getServerIpAddressUseCase,
            coroutineRule.dispatcher,
            coroutineRule.dispatcher
        )
    }

    @Test
    fun testOnStartStopButtonClick_startServer() = coroutineRule.runBlockingTest {
        val serverIpAddress = "192.168.1.100"
        val port = 8080

        every { getServerIpAddressUseCase() }.returns(serverIpAddress)

        mainViewModel.port.value = port.toString()
        mainViewModel.onStartStopButtonClick()

        verify { startServerUseCase(port) }
        assertEquals(true, mainViewModel.shouldDisablePortInputField.value)
        assertEquals(R.string.stop_server, mainViewModel.startStopButtonText.value)
        assertEquals(true, mainViewModel.shouldShowServerRunningIndicator.value)
        assertEquals(true, mainViewModel.shouldKeepScreenOn.value)
    }

    @Test
    fun testOnStartStopButtonClick_startServer_error() = coroutineRule.runBlockingTest {
        val port = 8080

        every { startServerUseCase(any()) }.throws(Exception())

        mainViewModel.port.value = port.toString()
        mainViewModel.onStartStopButtonClick()

        verify { startServerUseCase(port) }
        assertEquals(false, mainViewModel.shouldDisablePortInputField.value)
        assertEquals(R.string.start_server, mainViewModel.startStopButtonText.value)
        assertEquals(false, mainViewModel.shouldShowServerRunningIndicator.value)
        assertEquals(false, mainViewModel.shouldKeepScreenOn.value)
    }

    @Test
    fun testOnStartStopButtonClick_stopServer() = coroutineRule.runBlockingTest {
        testOnStartStopButtonClick_startServer()

        mainViewModel.onStartStopButtonClick()

        verify { stopServerUseCase() }
        assertEquals(false, mainViewModel.shouldDisablePortInputField.value)
        assertEquals(R.string.start_server, mainViewModel.startStopButtonText.value)
        assertEquals(false, mainViewModel.shouldShowServerRunningIndicator.value)
        assertEquals(false, mainViewModel.shouldKeepScreenOn.value)
    }

    @Test
    fun testOnStartStopButtonClick_stopServer_error() = coroutineRule.runBlockingTest {
        testOnStartStopButtonClick_startServer()

        every { stopServerUseCase() }.throws(Exception())

        mainViewModel.onStartStopButtonClick()

        verify { stopServerUseCase() }
        assertEquals(true, mainViewModel.shouldDisablePortInputField.value)
        assertEquals(R.string.stop_server, mainViewModel.startStopButtonText.value)
        assertEquals(true, mainViewModel.shouldShowServerRunningIndicator.value)
        assertEquals(true, mainViewModel.shouldKeepScreenOn.value)
    }

    @Test
    fun testOnActivityStopped() = coroutineRule.runBlockingTest {
        mainViewModel.onActivityStopped()
        verify { stopServerUseCase() }
    }
}