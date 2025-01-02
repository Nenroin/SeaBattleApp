package com.nenroin.seabattleapp.network

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

class GameClient(private val host: String, private val port: Int) {
    private lateinit var socket: Socket

    fun connect(onMessageReceived: (String) -> Unit) {
        Thread {
            try {
                socket = Socket(host, port)
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                val writer = socket.getOutputStream()

                while (true) {
                    val message = reader.readLine()
                    if (message != null) {
                        onMessageReceived(message)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun sendMessage(message: String) {
        val writer = socket.getOutputStream()
        writer.write("$message\n".toByteArray())
        writer.flush()
    }

    fun disconnect() {
        socket.close()
    }
}