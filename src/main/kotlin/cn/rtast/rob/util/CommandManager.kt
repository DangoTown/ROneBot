/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.util

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.util.ob.OneBotListener

class CommandManager {
    private val commands = mutableListOf<BaseCommand>()

    fun register(command: BaseCommand) {
        // this is not a suspend function, for thread safe
        commands.add(command)
    }

    internal suspend fun handlePrivate(listener: OneBotListener, message: PrivateMessage) {
        // execute when private message command triggered
        commands.find { command ->
            val firstWord = message.rawMessage.split(" ").firstOrNull() ?: ""
            command.commandNames.any { it == firstWord }
        }?.handlePrivate(listener, message)
    }

    internal suspend fun handleGroup(listener: OneBotListener, message: GroupMessage) {
        // execute when group message command triggered
        commands.find { command ->
            val firstWord = message.rawMessage.split(" ").firstOrNull() ?: ""
            command.commandNames.any { it == firstWord }
        }?.handleGroup(listener, message)
    }
}