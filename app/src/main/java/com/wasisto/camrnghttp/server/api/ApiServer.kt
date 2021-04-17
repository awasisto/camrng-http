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

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter.formatIpAddress
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.wasisto.camrnghttp.server.data.CamrngRandomDataSource
import com.wasisto.camrnghttp.server.domain.usecases.*
import com.wasisto.camrnghttp.servermanager.domain.interfaces.Server

class ApiServer(context: Context) : Server {

    private val camrngRandomDataSource = CamrngRandomDataSource(context)

    private val httpServer = AsyncHttpServer()

    private val controller =
        Controller(
            RandBytesUseCase(camrngRandomDataSource),
            RandBoolUseCase(camrngRandomDataSource),
            RandInt32UseCase(camrngRandomDataSource),
            RandUniformUseCase(camrngRandomDataSource),
            RandNormalUseCase(camrngRandomDataSource)
        )

    private val errorHandler = ErrorHandler()

    private val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    init {
        httpServer.get("/") { request, response -> controller.index(request, response) }
        httpServer.get("$API_PATH_PREFIX/$ACTION_RANDBYTES") { request, response -> try { controller.randBytes(request, response) } catch (t: Throwable) { errorHandler.handle(t, response) } }
        httpServer.get("$API_PATH_PREFIX/$ACTION_RANDBOOL") { request, response -> try { controller.randBool(request, response) } catch (t: Throwable) { errorHandler.handle(t, response) } }
        httpServer.get("$API_PATH_PREFIX/$ACTION_RANDINT32") { request, response -> try { controller.randInt32(request, response) } catch (t: Throwable) { errorHandler.handle(t, response) } }
        httpServer.get("$API_PATH_PREFIX/$ACTION_RANDUNIFORM") { request, response -> try { controller.randUniform(request, response) } catch (t: Throwable) { errorHandler.handle(t, response) } }
        httpServer.get("$API_PATH_PREFIX/$ACTION_RANDNORMAL") { request, response -> try { controller.randNormal(request, response) } catch (t: Throwable) { errorHandler.handle(t, response) } }
    }

    override fun start(port: Int) {
        require(port in 1024..65535) { "port out of range:$port" }
        camrngRandomDataSource.start()
        httpServer.listen(port)
    }

    override fun stop() {
        httpServer.stop()
        camrngRandomDataSource.stop()
    }

    @Suppress("DEPRECATION")
    override fun getIpAddress(): String {
        return formatIpAddress(wifiManager.connectionInfo.ipAddress)
    }
}