/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/19 07:49
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.onebot.v12.event.raw.message

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.onebot.v12.enums.internal.MessageType
import cn.rtast.rob.onebot.v12.enums.internal.PostType
import cn.rtast.rob.onebot.v12.enums.internal.SubType
import cn.rtast.rob.onebot.v12.event.raw.GroupSender
import cn.rtast.rob.onebot.v12.onebot12.OneBot12Action
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
public data class RawGroupMessageEvent(
    val anonymous: String? = null,
    val time: Long,
    @SerialName("message_id")
    val messageId: Long,
    val message: List<String>, // TODO
    @SerialName("user_id")
    val userId: Long,
    @SerialName("self_id")
    val selfId: Long,
    @SerialName("post_type")
    val postType: PostType,
    @SerialName("message_type")
    val messageType: MessageType,
    @SerialName("raw_message")
    val rawMessage: String,
    val sender: GroupSender,
    @SerialName("sub_type")
    val subType: SubType,
    val font: Int,
    override var sessionId: Uuid? = null
) : IGroupMessage {

    @Transient
    public lateinit var action: OneBot12Action
}