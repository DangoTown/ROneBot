/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ws

import cn.rtast.rob.util.onebot.MessageHandler
import cn.rtast.rob.util.onebot.OneBotListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

internal class WsServer(
    private val port: Int,
    private val accessToken: String,
    private val listener: OneBotListener,
    messageQueueLimit: Int
) : WebSocketServer(InetSocketAddress(port)) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val channelCoroutineScope = CoroutineScope(Dispatchers.IO)
    private val messageChannel = Channel<String>(messageQueueLimit)

    init {
        this.processMessages()
    }

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        handshake.iterateHttpFields().forEachRemaining {
            if (it == "Authorization") {
                val value = handshake.getFieldValue(it)
                if (value != "Bearer $accessToken") {
                    println("Websocket client's access token is not correct, disconnecting...")
                    conn.close(403, "Forbidden: Invalid or missing Authorization token")
                } else {
                    coroutineScope.launch {
                        MessageHandler.onOpen(listener, conn)
                    }
                }
            }
        }
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        coroutineScope.launch {
            MessageHandler.onClose(listener, code, reason, remote, conn)
        }
    }

    override fun onMessage(conn: WebSocket, message: String) {
        channelCoroutineScope.launch {
            messageChannel.send(message)
        }
    }

    override fun onError(conn: WebSocket, ex: Exception) {
        coroutineScope.launch {
            MessageHandler.onError(listener, ex)
        }
    }

    override fun onStart() {
        coroutineScope.launch {
            MessageHandler.onStart(listener, port)
        }
    }

    private fun processMessages() {
        coroutineScope.launch {
            for (message in messageChannel) {
                coroutineScope.launch {
                    MessageHandler.onMessage(listener, message)
                }
            }
        }
    }
}