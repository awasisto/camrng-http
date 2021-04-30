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

import com.wasisto.camrnghttp.server.domain.interfaces.Rng
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.Hex

class RandBytesUseCase(private val rng: Rng) {

    enum class Format {
        Base64, Hex
    }

    operator fun invoke(length: Int, format: Format): String =
        when (format) {
            Format.Base64 -> String(Base64.encodeBase64(ByteArray(length).apply { rng.randBytes(this) }))
            Format.Hex -> String(Hex.encodeHex(ByteArray(length).apply { rng.randBytes(this) }))
        }
}