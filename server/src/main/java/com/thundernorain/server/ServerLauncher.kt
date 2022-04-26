package com.thundernorain.server

import com.thundernorain.network_consts.SOCKET_PORT
import java.net.ServerSocket

fun main() {
    val serverSocket = ServerSocket(SOCKET_PORT)
    val gameStatsStorage = GameStatsStorage()
    println("Server started at PORT $SOCKET_PORT")
    while (true) {
        val socket = serverSocket.accept()
        println("Server connected to ${socket.inetAddress}")
        SocketServer(socket, gameStatsStorage).start()
        println("Server stopped")
    }
}