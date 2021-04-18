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

package com.wasisto.camrnghttp.servermanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wasisto.camrnghttp.servermanager.domain.usecases.GetServerIpAddressUseCase
import com.wasisto.camrnghttp.servermanager.domain.interfaces.Server
import com.wasisto.camrnghttp.servermanager.domain.usecases.StartServerUseCase
import com.wasisto.camrnghttp.servermanager.domain.usecases.StopServerUseCase
import com.wasisto.camrnghttp.servermanager.ui.main.MainViewModel

class ViewModelFactory(private val server: Server) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(
                        StartServerUseCase(server),
                        StopServerUseCase(server),
                        GetServerIpAddressUseCase(server)
                    )
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}