/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.session

import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IGroupSender
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.entity.IPrivateSender
import cn.rtast.rob.entity.ISender
import java.util.*

interface ISession {
    val id: UUID
    var active: Boolean
    val message: IMessage
    val command: IBaseCommand<IGroupMessage, IPrivateMessage>
    val sender: ISender

    fun endSession() {
        active = false
    }
}

interface IPrivateSession : ISession {
    override val message: IPrivateMessage
    override val sender: IPrivateSender
}

interface IGroupSession : ISession {
    override val message: IGroupMessage
    override val sender: IGroupSender
}