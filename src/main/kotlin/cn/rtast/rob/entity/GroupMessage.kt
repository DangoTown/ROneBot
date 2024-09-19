/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.ROneBotFactory.actionCoroutineScope
import cn.rtast.rob.entity.internal.MessageActionable
import cn.rtast.rob.util.ob.CQMessageChain
import cn.rtast.rob.util.ob.MessageChain
import cn.rtast.rob.util.ob.OBMessage
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class GroupMessage(
    @SerializedName("sub_type")
    val subType: String,
    @SerializedName("message_id")
    val messageId: Long,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("group_id")
    val groupId: Long,
    val message: List<ArrayMessage>,
    @SerializedName("raw_message")
    val rawMessage: String,
    var sender: Sender,
    val time: Long,
    val listener: OBMessage
) : MessageActionable {
    override suspend fun revoke(delay: Int) {
        super.revoke(delay)
        if (delay != 0) actionCoroutineScope.launch {
            delay(delay * 1000L)
            ROneBotFactory.action.revokeMessage(messageId)
        } else ROneBotFactory.action.revokeMessage(messageId)
    }

    override suspend fun reply(content: MessageChain) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        ROneBotFactory.action.sendGroupMessage(groupId, msg)
    }

    override suspend fun reply(content: String) {
        val msg = MessageChain.Builder().addText(content).build()
        this.reply(msg)
    }

    override suspend fun reply(content: CQMessageChain) {
        this.reply(content.finalString)
    }
}