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

package com.wasisto.camrnghttp.random

import android.content.Context
import com.wasisto.camrng.LensFacing
import com.wasisto.camrng.NoiseBasedCamRng
import com.wasisto.camrnghttp.server.domain.interfaces.RandomDataSource

class CamrngRandomDataSource(private val context: Context) : RandomDataSource {

    private var byteIterator: Iterator<Byte>? = null

    fun start() {
        NoiseBasedCamRng.reset()
        NoiseBasedCamRng.lensFacing = LensFacing.BACK
        byteIterator = NoiseBasedCamRng.newInstance(context)
            .apply {
                this.whiteningMethod = NoiseBasedCamRng.WhiteningMethod.INTERPIXEL_XOR
            }
            .getBytes()
            .blockingNext()
            .iterator()
    }

    fun stop() {
        NoiseBasedCamRng.reset()
        byteIterator = null
    }

    override fun randBytes(bytes: ByteArray) {
        byteIterator?.let { for (i in bytes.indices) bytes[i] = it.next() } ?: throw IllegalStateException()
    }
}