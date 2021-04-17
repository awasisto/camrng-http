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

package com.wasisto.camrnghttp.servermanager.domain.usecases

import com.wasisto.camrnghttp.servermanager.domain.interfaces.Server
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class StartServerUseCaseTest {

    private lateinit var startServerUseCase: StartServerUseCase

    @MockK
    private lateinit var server: Server

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        startServerUseCase = StartServerUseCase(server)
    }

    @Test
    fun testInvoke() {
        val port = 8080

        startServerUseCase(port)

        verify { server.start(port) }
    }
}