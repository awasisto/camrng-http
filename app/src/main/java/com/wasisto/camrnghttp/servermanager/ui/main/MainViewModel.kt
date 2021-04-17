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

import androidx.lifecycle.*
import com.wasisto.camrnghttp.R
import com.wasisto.camrnghttp.servermanager.domain.usecases.GetServerIpAddressUseCase
import com.wasisto.camrnghttp.servermanager.domain.usecases.StartServerUseCase
import com.wasisto.camrnghttp.servermanager.domain.usecases.StopServerUseCase
import com.wasisto.camrnghttp.servermanager.ui.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val startServerUseCase: StartServerUseCase,
    private val stopServerUseCase: StopServerUseCase,
    private val getServerIpAddressUseCase: GetServerIpAddressUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    val ipAddress = MutableLiveData<String>()

    val port = MutableLiveData("8080")

    val startStopButtonText = MutableLiveData(R.string.start_server)

    val shouldDisableStartStopButton = MutableLiveData(false)

    val shouldDisablePortInputField = MutableLiveData(false)

    val shouldShowServerRunningIndicator = MutableLiveData(false)

    val shouldKeepScreenOn = MutableLiveData(false)

    val showErrorEvent = MutableLiveData<Event<Unit>>()

    private var serverRunning = false

    fun onStartStopButtonClick() {
        if (!serverRunning) startServer() else stopServer()
    }

    fun onActivityStopped() {
        stopServer()
    }

    private fun startServer() {
        shouldDisablePortInputField.value = true
        shouldDisableStartStopButton.value = true

        viewModelScope.launch(ioDispatcher) {
            try {
                startServerUseCase(port.value!!.toInt())

                val serverIpAddress = getServerIpAddressUseCase()

                launch(mainDispatcher) {
                    ipAddress.value = serverIpAddress
                    startStopButtonText.value = R.string.stop_server
                    shouldShowServerRunningIndicator.value = true
                    shouldKeepScreenOn.value = true
                }

                serverRunning = true
            } catch (t: Throwable) {
                Timber.w(t)

                launch(mainDispatcher) {
                    shouldDisablePortInputField.value = false
                    showErrorEvent.value = Event(Unit)
                }
            }

            launch(mainDispatcher) {
                shouldDisableStartStopButton.value = false
            }
        }
    }

    private fun stopServer() {
        shouldDisableStartStopButton.value = true

        viewModelScope.launch(ioDispatcher) {
            try {
                stopServerUseCase()

                launch(mainDispatcher) {
                    shouldDisablePortInputField.value = false
                    startStopButtonText.value = R.string.start_server
                    shouldShowServerRunningIndicator.value = false
                    shouldKeepScreenOn.value = false
                }

                serverRunning = false
            } catch (t: Throwable) {
                Timber.w(t)

                launch(mainDispatcher) {
                    showErrorEvent.value = Event(Unit)
                }
            }

            launch(mainDispatcher) {
                shouldDisableStartStopButton.value = false
            }
        }
    }
}