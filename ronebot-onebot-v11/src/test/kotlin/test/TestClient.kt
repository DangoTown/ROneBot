/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.custom.ErrorEvent
import cn.rtast.rob.enums.MiniAppArkType
import cn.rtast.rob.onebot.OneBotListener

class TestClient : OneBotListener {

    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
    }

    override suspend fun onWebsocketErrorEvent(event: ErrorEvent) {
        event.exception.printStackTrace()
    }
}

val commands = listOf(
    EchoCommand(), DelayCommand(), MatchedCommand(),
)

suspend fun main() {
    val client = TestClient()
    val wsAddress = "ws://127.0.0.1:4646"
//    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken, client)
    ROneBotFactory.interceptor = CustomInterceptor()
    instance1.addListeningGroups(985927054)
    commands.forEach {
        ROneBotFactory.commandManager.register(it)
    }
}