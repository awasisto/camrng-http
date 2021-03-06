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
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.Hex
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test

class RandBytesUseCaseTest {

    private lateinit var randBytesUseCase: RandBytesUseCase

    @Before
    fun setUp() {
        randBytesUseCase = RandBytesUseCase(PseudoRng(seed = 0))
    }

    @Test
    fun testInvoke_base64() {
        val length = 42
        val expected = ByteArray(length).apply { PseudoRng(seed = 0).randBytes(this) }
        assertArrayEquals(expected, Base64.decodeBase64(randBytesUseCase(length, RandBytesUseCase.Format.Base64).toByteArray()))
    }

    @Test
    fun testInvoke_hex() {
        val length = 42
        val expected = ByteArray(length).apply { PseudoRng(seed = 0).randBytes(this) }
        assertArrayEquals(expected, Hex.decodeHex(randBytesUseCase(length, RandBytesUseCase.Format.Hex).toCharArray()))
    }
}