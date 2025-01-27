/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.command.arguments.AnyStringArgumentType
import cn.rtast.rob.command.arguments.CharArgumentType
import cn.rtast.rob.command.arguments.getAnyString
import cn.rtast.rob.command.arguments.getChar
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.custom.ErrorEvent
import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.onebot.sdl.messageChain
import cn.rtast.rob.segment.Text
import cn.rtast.rob.util.BaseCommand
import cn.rtast.rob.util.BrigadierCommand
import cn.rtast.rob.util.CommandSource
import cn.rtast.rob.util.Commands
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestClient : OneBotListener {

    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
        println(message)
        val msg = messageChain {
            addText("Hello World")
            this(Text("1111"))
            invoke(Text("2222"))
            +Text("22222")
        }
        message.reply(msg)
    }

    override suspend fun onWebsocketErrorEvent(event: ErrorEvent) {
        event.exception.printStackTrace()
    }
}

val commands = listOf(
    EchoCommand(), DelayCommand(), MatchedCommand(),
    ACommand()
)

class TestBrigadierCommand : BrigadierCommand() {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun register(dispatcher: CommandDispatcher<CommandSource>) {
        val root = LiteralArgumentBuilder.literal<CommandSource>("/foo")
            .then(
                RequiredArgumentBuilder.argument<CommandSource, String>("bar", StringArgumentType.string())
                    .executes {
                        scope.launch {
                            try {
                                println(StringArgumentType.getString(it, "bar"))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        0
                    }
            )
            .then(
                LiteralArgumentBuilder.literal<CommandSource>("ss")
                    .then(
                        RequiredArgumentBuilder.argument<CommandSource, Any>(
                            "any",
                            AnyStringArgumentType.anyStringType()
                        )
                            .executes {
                                println(it.getAnyString("any"))
                                println(AnyStringArgumentType.getAnyString(it, "any")::class.java)
                                0
                            }
                    )
            ).then(
                LiteralArgumentBuilder.literal<CommandSource>("char")
                    .then(
                        RequiredArgumentBuilder.argument<CommandSource, Char>("char", CharArgumentType.char())
                            .executes {
                                println(CharArgumentType.getChar(it, "char")::class.java)
                                0
                            }
                    )
            )
        dispatcher.register(root)
        dispatcher.register(LiteralArgumentBuilder.literal<CommandSource>("/test").redirect(root.build()))
    }
}

class ACommand : BaseCommand() {
    override val commandNames = listOf("/1")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        println(message.action.joinFriendFaceChain(message.sender.userId, 754571597L, QQFace.AoMan))
        println(message.message)
    }
}

suspend fun main() {
    val client = TestClient()
//    val wsAddress = "ws://127.0.0.1:4646"
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken, client).apply {
        println(this)
    }
    instance1.addListeningGroup(985927054)
    ROneBotFactory.brigadierCommandManager.register(TestBrigadierCommand())
    ROneBotFactory.brigadierCommandManager.register(
        Commands.literal("main")
            .then(
                Commands.argument("test", CharArgumentType.char())
                    .executes {
                        println(it.getChar("test"))
                        Command.SINGLE_SUCCESS
                    }
            ), listOf("main1", "1111")
    )
    commands.forEach {
        ROneBotFactory.commandManager.register(it)
    }
}