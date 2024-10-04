/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.ROneBotFactory.actionCoroutineScope
import cn.rtast.rob.actionable.GroupMessageActionable
import cn.rtast.rob.actionable.MessageActionable
import cn.rtast.rob.entity.lagrange.ForwardMessageId
import cn.rtast.rob.util.ob.CQMessageChain
import cn.rtast.rob.util.ob.MessageChain
import cn.rtast.rob.util.ob.NodeMessageChain
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
    var sender: GroupSender,
    val time: Long,
) : GroupMessageActionable {
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

    override suspend fun reply(content: NodeMessageChain, async: Boolean): ForwardMessageId.Data? {
        if (async) {
            ROneBotFactory.action.sendGroupForwardMsgAsync(groupId, content)
            return null
        } else {
            return ROneBotFactory.action.sendGroupForwardMsg(groupId, content)
        }
    }

    override suspend fun reaction(code: String) {
        ROneBotFactory.action.reaction(groupId, messageId, code)
    }

    override suspend fun unsetReaction(code: String) {
        ROneBotFactory.action.reaction(groupId, messageId, code, false)
    }

    override suspend fun setEssence() {
        ROneBotFactory.action.setEssenceMessage(messageId)
    }

    override suspend fun deleteEssence() {
        ROneBotFactory.action.deleteEssenceMessage(messageId)
    }

    override suspend fun markAsRead() {
        ROneBotFactory.action.markAsRead(messageId)
    }
}

data class PrivateMessage(
    @SerializedName("sub_type")
    val subType: String,
    @SerializedName("message_id")
    val messageId: Long,
    @SerializedName("user_id")
    val userId: Long,
    val message: List<ArrayMessage>,
    @SerializedName("raw_message")
    val rawMessage: String,
    val sender: PrivateSender,
    val time: Long,
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
        ROneBotFactory.action.sendPrivateMessage(userId, msg)
    }

    override suspend fun reply(content: String) {
        val msg = MessageChain.Builder().addText(content).build()
        this.reply(msg)
    }

    override suspend fun reply(content: CQMessageChain) {
        this.reply(content.finalString)
    }

    override suspend fun reply(content: NodeMessageChain, async: Boolean): ForwardMessageId.Data? {
        if (async) {
            ROneBotFactory.action.sendPrivateForwardMsgAsync(sender.userId, content)
            return null
        } else {
            return ROneBotFactory.action.sendPrivateForwardMsg(sender.userId, content)
        }
    }

    override suspend fun markAsRead() {
        ROneBotFactory.action.markAsRead(messageId)
    }
}