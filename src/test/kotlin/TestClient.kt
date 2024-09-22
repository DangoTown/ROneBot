/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.util.ob.MessageChain
import cn.rtast.rob.util.ob.OneBotListener

fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val rob = ROneBotFactory.createClient(wsAddress, wsAccessToken, object : OneBotListener {

        override suspend fun onWebsocketOpenEvent() {
            this.broadcastMessage(MessageChain.Builder().addText("sda").build())
        }
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
        }
    })
    rob.commandManager.register(EchoCommand())  // not a suspend function
    rob.commandManager.register(DelayCommand())  // not a suspend function
//    rob.action.sendGroupMessage(114514, "1919810")  // send a message in global scope
    rob.addListeningGroups(985927054, 114514)  // set listening groups, set empty to listen all groups' event
}