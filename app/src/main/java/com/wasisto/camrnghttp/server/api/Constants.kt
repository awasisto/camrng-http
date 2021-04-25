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

const val REST_API_PATH_PREFIX = "/api"

const val CONTENT_TYPE_JSON = "application/json"

const val PARAM_LENGTH = "length"
const val PARAM_FORMAT = "format"
const val PARAM_MIN = "min"
const val PARAM_MAX = "max"
const val PARAM_MU = "mu"
const val PARAM_SIGMA = "sigma"

const val JSON_ATTRIBUTE_ACTION = "action"
const val JSON_ATTRIBUTE_MESSAGE = "message"
const val JSON_ATTRIBUTE_DATA = "data"
const val JSON_ATTRIBUTE_LENGTH = "length"
const val JSON_ATTRIBUTE_FORMAT = "format"
const val JSON_ATTRIBUTE_MIN = "min"
const val JSON_ATTRIBUTE_MAX = "max"
const val JSON_ATTRIBUTE_MU = "mu"
const val JSON_ATTRIBUTE_SIGMA = "sigma"

const val ERROR_VALIDATION_FAILED = "Validation Failed"
const val ERROR_SERVER_ERROR = "Server Error"

const val ACTION_RANDBYTES = "randbytes"
const val ACTION_RANDBOOL = "randbool"
const val ACTION_RANDINT32 = "randint32"
const val ACTION_RANDUNIFORM = "randuniform"
const val ACTION_RANDNORMAL = "randnormal"

const val FORMAT_BASE64 = "base64"
const val FORMAT_HEX = "hex"