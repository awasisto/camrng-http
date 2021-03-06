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

package com.wasisto.camrnghttp.server.domain.usecases

import com.wasisto.camrnghttp.rng.PseudoRng
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RandBoolUseCaseTest {

    private lateinit var randBoolUseCase: RandBoolUseCase

    @Before
    fun setUp() {
        randBoolUseCase = RandBoolUseCase(PseudoRng(seed = 0))
    }

    @Test
    fun testInvoke() {
        val length = 1000

        val generated = randBoolUseCase(length)

        assertEquals(0.5, generated.count { it }.toDouble() / generated.size, 0.1)
        assertEquals(length, generated.size)
    }
}