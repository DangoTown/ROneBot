/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */

package rob

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.ob.OneBotListener


//fun main() {
//    val factory = ROneBotFactory()
//    val rob = factory.createServer(6760, "114514", object : OneBotListener {
//        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
//            println(message.rawMessage)
//            rob
//        }
//    })
//
//    rob.commandManager.register(EchoCommand())
//}