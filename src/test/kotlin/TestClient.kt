/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.segment.Node
import cn.rtast.rob.segment.PlainText
import cn.rtast.rob.util.ob.NodeMessageChain
import cn.rtast.rob.util.ob.OneBotListener

fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val rob = ROneBotFactory.createClient(wsAddress, wsAccessToken, object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            println(message.rawMessage)
            val nodeMsg = NodeMessageChain.Builder()
                .addNode(
                    Node(
                        data =
                        Node.Data(
                            "RTAkland",
                            "3458671395",
                            listOf(PlainText(data = PlainText.Data("测试")))
                        )
                    )
                ).build()
            this.sendPrivateForwardMsg(message.sender.userId, nodeMsg)
        }
    })
    rob.commandManager.register(EchoCommand())  // not a suspend function
    rob.commandManager.register(DelayCommand())  // not a suspend function
    rob.addListeningGroups(985927054, 114514)  // set listening groups, set empty to listen all groups' event
}