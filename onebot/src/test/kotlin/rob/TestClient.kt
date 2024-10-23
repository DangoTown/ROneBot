/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package rob

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.util.ob.OneBotListener

class TestClient : OneBotListener {
    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
        println(message.text)
        println(message.texts)
        println(message.mfaces)
        println(message.mface)
    }

    override suspend fun onWebsocketErrorEvent(ex: Exception) {
        ex.printStackTrace()
    }
}

val wsAddress = System.getenv("WS_ADDRESS")
val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
val factory = ROneBotFactory()
val rob = factory.createClient(wsAddress, wsAccessToken, TestClient())

fun main() {
    rob.commandManager.register(EchoCommand())  // not a suspend function
    rob.commandManager.register(DelayCommand())  // not a suspend function
    rob.commandManager.register(MatchedCommand())  // not a suspend function
    rob.scheduler.scheduleTask(suspend {
//        println(rob.action.getStatus())
    }, 1000L, 1000L)
    rob.addListeningGroups(985927054)
}