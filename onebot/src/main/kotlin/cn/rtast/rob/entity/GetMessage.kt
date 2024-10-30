/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/1
 */

@file:Suppress("unused")

package cn.rtast.rob.entity

import cn.rtast.rob.entity.GetMessage.Message
import cn.rtast.rob.enums.ArrayMessageType
import cn.rtast.rob.enums.MessageType
import com.google.gson.annotations.SerializedName

data class GetMessage(
    val data: Message,
    val echo: String?
) {
    data class Message(
        val time: Long,
        @SerializedName("message_type")
        val messageType: MessageType,
        val message: List<ArrayMessage>,
        @SerializedName("message_id")
        val messageId: Long,
        val sender: GroupSender,
        val id: String?,
    )
}

/**
 * 快速从一个数组消息中获取所有的文字部分
 * 返回一个字符串列表
 */
val Message.texts get() = this.message.filter { it.type == ArrayMessageType.text }.mapNotNull { it.data.text }


/**
 * 快速从一个数组消息中获取所有的文字部分
 * 返回一个拼接好的字符串
 */
val Message.text
    get() = this.message.filter { it.type == ArrayMessageType.text }.mapNotNull { it.data.text }
        .joinToString("")

/**
 * 快速从一个数组消息中获取图片(包括普通图片和表情包)
 * 返回一个[MessageData.Image]数组
 */
val Message.images
    get() = this.message.filter { it.type == ArrayMessageType.image }.map { it.data }
        .map { MessageData.Image(it.file!!, it.filename!!, it.url!!, it.summary!!, it.subType!!) }

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.MFace]数组
 */
val Message.mfaces
    get() = this.message.filter { it.type == ArrayMessageType.mface }.map { it.data }
        .map { MessageData.MFace(it.emojiId!!, it.emojiPackageId!!, it.key!!, it.url!!, it.summary!!) }

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.MFace]对象
 */
val Message.mface
    get() = this.message.filter { it.type == ArrayMessageType.mface }.map { it.data }
        .map { MessageData.MFace(it.emojiId!!, it.emojiPackageId!!, it.key!!, it.url!!, it.summary!!) }
        .firstOrNull()

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.Face]数组
 */
val Message.faces
    get() = this.message.filter { it.type == ArrayMessageType.face }
        .map { MessageData.Face(it.data.id.toString(), it.data.large) }