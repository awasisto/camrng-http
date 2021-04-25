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

import com.wasisto.camrnghttp.random.PrngRandomDataSource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RandInt32UseCaseTest {

    private lateinit var randInt32UseCase: RandInt32UseCase

    @Before
    fun setUp() {
        randInt32UseCase = RandInt32UseCase(PrngRandomDataSource(seed = 0))
    }

    @Test
    fun testInvoke() {
        val min = -13
        val max = 42
        val length = 10000

        val generated = randInt32UseCase(min, max, length)

        for (x in generated) {
            assertTrue(x in min..max)
        }

        for (i in min..max) {
            assertTrue(i in generated)
        }

        assertEquals(length, generated.size)
    }
}