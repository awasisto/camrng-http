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

import com.wasisto.camrnghttp.server.data.PrngRandomDataSource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RandUniformUseCaseTest {

    private lateinit var randUniformUseCase: RandUniformUseCase

    @Before
    fun setUp() {
        randUniformUseCase = RandUniformUseCase(PrngRandomDataSource(seed = 0))
    }

    @Test
    fun testInvoke() {
        val min = -2.7
        val max = 3.1
        val length = 10000

        val generated = randUniformUseCase(min, max, length)

        for (x in generated) {
            assertTrue(x in min..max)
        }

        assertEquals(min, generated.minOrNull()!!, 0.1)
        assertEquals(max, generated.maxOrNull()!!, 0.1)
        assertEquals(length, generated.size)
    }
}