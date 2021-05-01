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

package com.wasisto.camrnghttp.server.api

import com.google.gson.Gson
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import java.util.*
import com.google.gson.GsonBuilder
import com.wasisto.camrnghttp.server.domain.usecases.*

class RestApiController(
    private val randBytesUseCase: RandBytesUseCase,
    private val randBoolUseCase: RandBoolUseCase,
    private val randInt32UseCase: RandInt32UseCase,
    private val randUniformUseCase: RandUniformUseCase,
    private val randNormalUseCase: RandNormalUseCase,
    private val gson: Gson
) {
    fun randBytes(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        val length = request[PARAM_LENGTH]?.toIntOrNull() ?: throw IllegalArgumentException()
        require(length > 0)
        var formatString = request[PARAM_FORMAT]?.toLowerCase(Locale.US)
        if (formatString == null) formatString = FORMAT_BASE64
        val format = when (formatString.toLowerCase(Locale.US)) {
            FORMAT_HEX -> RandBytesUseCase.Format.Hex
            else -> RandBytesUseCase.Format.Base64
        }
        response.send(CONTENT_TYPE_JSON, gson.toJson(mapOf(
            Pair(JSON_ATTRIBUTE_ACTION, ACTION_RANDBYTES),
            Pair(JSON_ATTRIBUTE_LENGTH, length),
            Pair(JSON_ATTRIBUTE_FORMAT, formatString),
            Pair(JSON_ATTRIBUTE_DATA, randBytesUseCase(length, format))
        )))
    }

    fun randBool(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        var length = request[PARAM_LENGTH]?.toIntOrNull()
        if (length == null) length = 1
        response.send(CONTENT_TYPE_JSON, gson.toJson(mapOf(
            Pair(JSON_ATTRIBUTE_ACTION, ACTION_RANDBOOL),
            Pair(JSON_ATTRIBUTE_LENGTH, length),
            Pair(JSON_ATTRIBUTE_DATA, randBoolUseCase(length))
        )))
    }

    fun randInt32(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        var length = request[PARAM_LENGTH]?.toIntOrNull()
        if (length == null) length = 1
        var min = request[PARAM_MIN]?.toIntOrNull()
        if (min == null) min = Int.MIN_VALUE
        var max = request[PARAM_MAX]?.toIntOrNull()
        if (max == null) max = Int.MAX_VALUE
        if (min > max) {
            min = Int.MIN_VALUE
            max = Int.MAX_VALUE
        }
        response.send(CONTENT_TYPE_JSON, gson.toJson(mapOf(
            Pair(JSON_ATTRIBUTE_ACTION, ACTION_RANDINT32),
            Pair(JSON_ATTRIBUTE_MIN, min),
            Pair(JSON_ATTRIBUTE_MAX, max),
            Pair(JSON_ATTRIBUTE_LENGTH, length),
            Pair(JSON_ATTRIBUTE_DATA, randInt32UseCase(min, max, length))
        )))
    }

    fun randUniform(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        var length = request[PARAM_LENGTH]?.toIntOrNull()
        if (length == null) length = 1
        var min = request[PARAM_MIN]?.toDoubleOrNull()
        if (min == null) min = 0.0
        var max = request[PARAM_MAX]?.toDoubleOrNull()
        if (max == null) max = 1.0
        if (min > max) {
            min = 0.0
            max = 1.0
        }
        response.send(CONTENT_TYPE_JSON, gson.toJson(mapOf(
            Pair(JSON_ATTRIBUTE_ACTION, ACTION_RANDUNIFORM),
            Pair(JSON_ATTRIBUTE_MIN, min),
            Pair(JSON_ATTRIBUTE_MAX, max),
            Pair(JSON_ATTRIBUTE_LENGTH, length),
            Pair(JSON_ATTRIBUTE_DATA, randUniformUseCase(min, max, length))
        )))
    }

    fun randNormal(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        var length = request[PARAM_LENGTH]?.toIntOrNull()
        if (length == null) length = 1
        var mu = request[PARAM_MU]?.toDoubleOrNull()
        if (mu == null) mu = 0.0
        var sigma = request[PARAM_SIGMA]?.toDoubleOrNull()
        if (sigma == null) sigma = 1.0
        response.send(CONTENT_TYPE_JSON, gson.toJson(mapOf(
            Pair(JSON_ATTRIBUTE_ACTION, ACTION_RANDNORMAL),
            Pair(JSON_ATTRIBUTE_MU, mu),
            Pair(JSON_ATTRIBUTE_SIGMA, sigma),
            Pair(JSON_ATTRIBUTE_LENGTH, length),
            Pair(JSON_ATTRIBUTE_DATA, randNormalUseCase(mu, sigma, length))
        )))
    }
}