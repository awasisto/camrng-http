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
import org.apache.commons.rng.UniformRandomProvider
import org.apache.commons.rng.sampling.distribution.ZigguratNormalizedGaussianSampler
import java.nio.ByteBuffer

class RandNormalUseCase(rng: Rng) {

    companion object {
        private val samplerInstances = mutableMapOf<Rng, ZigguratNormalizedGaussianSampler>()

        private fun getSamplerInstance(rng: Rng) =
            samplerInstances[rng]
                ?: ZigguratNormalizedGaussianSampler(
                    object : UniformRandomProvider {
                        override fun nextLong() = ByteBuffer.wrap(ByteArray(8).apply { rng.randBytes(this) }).long
                        override fun nextDouble() = (nextLong() ushr 11) / (1L shl 53).toDouble()
                        override fun nextBytes(bytes: ByteArray) = throw NotImplementedError()
                        override fun nextBytes(bytes: ByteArray, start: Int, len: Int) = throw NotImplementedError()
                        override fun nextInt() = throw NotImplementedError()
                        override fun nextInt(n: Int) = throw NotImplementedError()
                        override fun nextLong(n: Long) = throw NotImplementedError()
                        override fun nextBoolean() = throw NotImplementedError()
                        override fun nextFloat() = throw NotImplementedError()
                    }
                ).also {
                    samplerInstances[rng] = it
                }
    }

    private val sampler = getSamplerInstance(rng)

    operator fun invoke(mu: Double, sigma: Double, length: Int) = List(length) { mu + sampler.sample() * sigma }
}