/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.interceptor

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage


/**
 * 实现了拦截器
 */
public abstract class CommandInterceptor :
    ICommandInterceptor<BaseCommand, GroupMessage, PrivateMessage> {
    final override suspend fun handleGroupInterceptor(
        message: GroupMessage,
        command: BaseCommand,
        block: suspend (GroupMessage) -> Unit
    ): Unit = super.handleGroupInterceptor(message, command, block)


    final override suspend fun handlePrivateInterceptor(
        message: PrivateMessage,
        command: BaseCommand,
        block: suspend (PrivateMessage) -> Unit
    ): Unit = super.handlePrivateInterceptor(message, command, block)

}

/**
 * 当用户没有设置指令拦截器时使用默认的拦截器
 * 即: 继续执行任何指令, 执行完成之后不做任何操作
 */
internal val defaultInterceptor = object : CommandInterceptor() {}
