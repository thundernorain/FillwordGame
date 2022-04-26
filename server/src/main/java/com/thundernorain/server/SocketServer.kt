package com.thundernorain.server

import com.google.gson.Gson
import com.thundernorain.server.creator.FillwordResponseCreator
import com.thundernorain.server.model.check_word.CheckWordRequest
import com.thundernorain.server.model.check_word.CheckWordResponse
import com.thundernorain.server.model.check_word.CheckWordResult
import com.thundernorain.server.model.fillword_game.FillwordRequest
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import java.nio.charset.StandardCharsets

class SocketServer(
    socket: Socket,
    private val gameStats: GameStatsStorage
) : Thread() {

    private val writer = OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)
    private val reader = BufferedReader(
        InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
    )

    private val gson = Gson()

    override fun run() {
        while (true) {
            println("Waiting for request...")

            val request = reader.readLine()

            println("Received request")

            if (request == null) {
                println("request was null")
                break
            }

            val fillwordRequest = deserializeJsonRequest<FillwordRequest?>(request)
            val checkWordRequest = deserializeJsonRequest<CheckWordRequest?>(request)

            fillwordRequest?.difficulty?.let { sendFillwordResponse(fillwordRequest) }
            checkWordRequest?.word?.let { sendCheckWordResponse(checkWordRequest) }

            writer.flush()
        }
    }

    private inline fun <reified T> deserializeJsonRequest(json: String): T? {
        val result = runCatching { gson.fromJson(json, T::class.java) }
            .getOrNull()
        println("$LOG_TAG : Result of deserialization ${T::class.java} : $result")
        return result
    }

    private inline fun <reified T : Any> serializeJson(src: T): String? {
        val result = runCatching { gson.toJson(src) }
            .getOrNull()
        println("$LOG_TAG : Result of serialization ${T::class.java} : $result")
        return result
    }

    private fun sendFillwordResponse(request: FillwordRequest) {
        gameStats.onNewGameStarted()
        val response = FillwordResponseCreator().create(
            request.difficulty,
            gameStats,
            gson
        )
        val jsonString = serializeJson(response)
        jsonString?.let { writer.write(it + "\n") }
    }

    private fun sendCheckWordResponse(request: CheckWordRequest) {
        val checkResult = if (gameStats.checkWordInList(request.word)) {
            CheckWordResult.CORRECT
        } else {
            CheckWordResult.WRONG
        }
        val checkResponse = CheckWordResponse(checkResult)
        val jsonString = serializeJson(checkResponse)
        jsonString?.let { writer.write(it + "\n") }
    }

    companion object {
        private const val LOG_TAG = "SocketServer"
        const val WORDS_FILE = """.\server\src\main\resources\words.json"""
    }
}