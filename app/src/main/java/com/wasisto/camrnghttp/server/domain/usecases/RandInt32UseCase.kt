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
import java.nio.ByteBuffer

class RandInt32UseCase(private val randomDataSource: RandomDataSource) {

    operator fun invoke(min: Int, max: Int, length: Int) =
        List(length) {
            val s = max - min + 1
            if (s > 0) {
                // D. Lemire, "Fast Random Integer Generation in an Interval", ACM Transactions on Modeling and Computer
                // Simulation, vol. 29, no. 1, pp. 1–12, 2019.
                var m = ((rand().toLong() and 0xffffffffL) * s)
                var l = m and 0xffffffffL
                if (l < s) {
                    val t: Long = 0x100000000L % s
                    while (l < t) {
                        m = (rand().toLong() and 0xffffffffL) * s
                        l = m and 0xffffffffL
                    }
                }
                return@List min + (m ushr 32).toInt()
            } else {
                var x = rand()
                while (x !in min..max) x = rand()
                return@List x
            }
        }

    private fun rand() = ByteBuffer.wrap(ByteArray(4).apply { randomDataSource.randBytes(this) }).int
}