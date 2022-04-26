package com.thundernorain.fillwordgame.data.api

import android.util.Log
import com.google.gson.Gson
import com.thundernorain.fillwordgame.domain.api.GameApi
import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordRequest
import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordResponse
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordRequest
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordResponse
import com.thundernorain.network_consts.SOCKET_HOST
import com.thundernorain.network_consts.SOCKET_PORT
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import java.nio.charset.StandardCharsets

class GameApiImpl : GameApi {

    private var socket: Socket? = null

    override suspend fun getGameField(
        request: FillwordRequest
    ) = requestWithResponseData<FillwordResponse>(request)

    override suspend fun checkWord(
        request: CheckWordRequest
    ) = requestWithResponseData<CheckWordResponse>(request)

    override suspend fun closeConnection() {
        socket?.let {
            if (!it.isClosed) runCatching {
                it.close()
            }
        }
        socket = null
    }

    private suspend inline fun <reified T : Any> requestWithResponseData(request: Any): T {
        checkForConnectionAndOpen()
        socket?.let {
            val gson = Gson()
            val requestJson = gson.toJson(request)
            sendJson(requestJson, it)
            val response = getJson(it)
            return gson.fromJson(response, T::class.java)
        } ?: throw NullPointerException()
    }

    private fun sendJson(json: String, socket: Socket) {
        runBlocking {
            OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8).apply {
                write(json + "\n")
                flush()
                Log.d("ClientSocket", "Written json: $json")
            }
        }
    }

    private fun getJson(socket: Socket): String {
        var response = ""
        runBlocking {
            BufferedReader(InputStreamReader(socket.getInputStream())).apply {
                response = readLine()
            }
        }
        return response
    }

    private suspend fun checkForConnectionAndOpen() {
        if (socket != null) return
        openConnection()
    }

    private suspend fun openConnection() {
        socket = Socket(SOCKET_HOST, SOCKET_PORT)
        delay(1000)
    }
}