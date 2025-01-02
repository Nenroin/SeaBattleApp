package com.nenroin.seabattleapp.network

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.ServerSocket

class GameServer(private val port: Int) {
    private lateinit var serverSocket: ServerSocket

    fun startServer(onMessageReceived: (String) -> Unit) {
        serverSocket = ServerSocket(port)
        Thread {
            try {
                val clientSocket = serverSocket.accept()
                val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                val writer = clientSocket.getOutputStream()

                while (true) {
                    val message = reader.readLine()
                    if (message != null) {
                        onMessageReceived(message)
                        sendMessage(writer, "ACK: $message")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun sendMessage(outputStream: OutputStream, message: String) {
        outputStream.write("$message\n".toByteArray())
        outputStream.flush()
    }

    fun stopServer() {
        serverSocket.close()
    }
}