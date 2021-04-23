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

import com.wasisto.camrnghttp.server.domain.interfaces.RandomDataSource

class RandBoolUseCase(private val randomDataSource: RandomDataSource) {
    operator fun invoke(length: Int): List<Boolean> =
        ArrayList<Boolean>(length).apply {
            val bytes = ByteArray((length shr 3) + 1).apply { randomDataSource.randBytes(this) }
            for (byte in bytes) {
                for (i in 0 until 8) {
                    this += byte.toInt() shr i and 1 == 1
                    if (size == length) return@apply
                }
            }
        }
}