/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */


import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.BaseCommand
import cn.rtast.rob.util.ob.CQMessageChain
import cn.rtast.rob.util.ob.OneBotListener

class EchoCommand : BaseCommand() {
    // A simple echo message command
    override val commandNames = listOf("/echo", "/eee")

    override suspend fun executeGroup(listener: OneBotListener, message: GroupMessage, args: List<String>) {
        val msg = CQMessageChain.Builder()
            .addReply(message.messageId)
            .addText(args.joinToString(" "))
            .build()
        listener.sendGroupMessage(message.groupId, msg)
    }
}