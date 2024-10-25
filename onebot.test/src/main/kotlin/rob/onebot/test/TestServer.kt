/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */

package rob.onebot.test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.ob.OneBotListener


fun main() {
    val rob = ROneBotFactory.createServer(6760, "114514", object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            println(message.rawMessage)
        }
    })

    rob.commandManager.register(EchoCommand())
}