/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob

import cn.rtast.rob.util.MessageCommand
import cn.rtast.rob.util.ob.OBMessage
import cn.rtast.rob.util.ws.WsClient
import cn.rtast.rob.util.ws.WsServer
import org.java_websocket.client.WebSocketClient
import org.java_websocket.server.WebSocketServer


object ROneBotFactory {

    private lateinit var wsClient: WebSocketClient
    private lateinit var wsServer: WebSocketServer

    val commandManager = MessageCommand()

    fun WebSocketClient.getCommandManager(): MessageCommand {
        return commandManager
    }

    fun WebSocketServer.getCommandManager(): MessageCommand {
        return commandManager
    }

    @JvmOverloads
    fun createClient(
        address: String,
        accessToken: String,
        listener: OBMessage,
        alsoConnect: Boolean = true
    ): WebSocketClient {
        wsClient = WsClient(address, accessToken, listener).also { if (alsoConnect) it.connect() }
        return wsClient
    }

    fun close() {
        wsClient.close()
    }

    @JvmOverloads
    fun createServer(port: Int, listener: OBMessage, alsoStart: Boolean = true): WebSocketServer {
        wsServer = WsServer(port, listener).also { if (alsoStart) it.start() }
        return wsServer
    }

    fun start() {
        wsServer.start()
    }
}