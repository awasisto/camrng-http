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
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import timber.log.Timber

class ErrorHandler {

    private val gson = Gson()

    fun handle(t: Throwable, response: AsyncHttpServerResponse) {
        when(t) {
            is IllegalArgumentException -> {
                response.code(422).send(
                    CONTENT_TYPE_JSON, gson.toJson(mapOf(
                    Pair(JSON_ATTRIBUTE_MESSAGE, ERROR_VALIDATION_FAILED)
                )))
            }
            else -> {
                Timber.w(t)
                response.code(500).send(
                    CONTENT_TYPE_JSON, gson.toJson(mapOf(
                    Pair(JSON_ATTRIBUTE_MESSAGE, ERROR_SERVER_ERROR)
                )))
            }
        }
    }
}