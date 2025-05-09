/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */

@file:Suppress("unused")
@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.qqbot.qbot

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.common.http.Http
import cn.rtast.rob.qqbot.ACCESS_TOKEN_URL
import cn.rtast.rob.qqbot.BotInstance
import cn.rtast.rob.qqbot.ROOT_API_URL
import cn.rtast.rob.qqbot.api.SendKeyboardMessage
import cn.rtast.rob.qqbot.api.SendMarkdownMessage
import cn.rtast.rob.qqbot.api.SendPlainTextMessage
import cn.rtast.rob.qqbot.entity.internal.GetAccessTokenPayload
import cn.rtast.rob.qqbot.entity.internal.GetAccessTokenResponse
import cn.rtast.rob.qqbot.enums.internal.HTTPMethod
import cn.rtast.rob.qqbot.segment.Keyboard
import cn.rtast.rob.qqbot.segment.Markdown
import cn.rtast.rob.util.toJson
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmSynthetic

public class QQBotAction internal constructor(
    private val appId: String,
    private val clientSecret: String,
    private val botInstance: BotInstance,
) : SendActionMoreExt {

    private var _count: Int = 0

    /**
     * 自增消息序列号
     */
    internal var messageSeq: Int
        get() {
            _count += 1
            return _count
        }
        private set(value) {
            _count = value
        }

    @JvmSynthetic
    internal fun getAccessToken(): String {
        val payload = GetAccessTokenPayload(appId, clientSecret).toJson()
        val response = Http.post<GetAccessTokenResponse>(ACCESS_TOKEN_URL, payload)
        return response.accessToken
    }

    override suspend fun send(method: HTTPMethod, api: String, payload: Any?): String {
        return when (method) {
            HTTPMethod.GET -> Http.get(api, headers = mapOf("Authorization" to "QQBot ${this.getAccessToken()}"))
            HTTPMethod.POST -> this.send(api, payload)
            HTTPMethod.PUT -> Http.put(
                "$ROOT_API_URL/$api",
                payload?.toJson() ?: "{}",
                headers = mapOf("Authorization" to "QQBot ${this.getAccessToken()}")
            )

            HTTPMethod.DELETE -> Http.delete(
                "$ROOT_API_URL/$api",
                payload?.toJson() ?: "{}",
                headers = mapOf("Authorization" to "QQBot ${this.getAccessToken()}")
            )
        }
    }

    override suspend fun <T> send(api: String, payload: T?): String {
        val response = Http.post(
            "$ROOT_API_URL/$api", payload?.toJson() ?: "{}",
            headers = mapOf("Authorization" to "QQBot ${this.getAccessToken()}")
        )
        return response
    }

    /**
     * 发送私聊纯文本消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendPrivatePlainTextMessage(
        openId: String,
        content: String,
        eventId: String,
        msgId: String,
    ) {
        val payload = SendPlainTextMessage(content, eventId, msgId, messageSeq)
        this.send("v2/users/$openId/messages", payload)
    }

    /**
     * 发送单聊markdown消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendPrivateMarkdownMessage(
        openId: String,
        content: Markdown,
        eventId: String,
        msgId: String,
    ) {
        val payload = SendMarkdownMessage(content, eventId, msgId, messageSeq)
        this.send("v2/users/$openId/messages", payload)
    }

    /**
     * 发送单聊键盘格消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendPrivateKeyboardMessage(
        openId: String,
        content: Keyboard,
        eventId: String,
        msgId: String,
    ) {
        val payload = SendKeyboardMessage(content, eventId, msgId, messageSeq)
        this.send("v2/users/$openId/messages", payload)
    }

    /**
     * 发送群聊纯文本消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupPlainTextMessage(openId: String, content: String, eventId: String, msgId: String) {
        val payload = SendPlainTextMessage(content, eventId, msgId, messageSeq)
        this.send("v2/groups/$openId/messages", payload)
    }

    /**
     * 发送群聊markdown消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupMarkdownMessage(
        openId: String,
        content: Markdown,
        eventId: String,
        msgId: String,
    ) {
        val payload = SendMarkdownMessage(content, eventId, msgId, messageSeq)
        this.send("v2/groups/$openId/messages", payload)
    }

    /**
     * 发送群聊键盘格消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupKeyboardMessage(
        openId: String,
        content: Keyboard,
        eventId: String,
        msgId: String,
    ) {
        val payload = SendKeyboardMessage(content, eventId, msgId, messageSeq)
        this.send("v2/groups/$openId/messages", payload)
    }

    /**
     * 撤回单聊消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun revokePrivateMessage(openId: String, messageId: String) {
        this.send(HTTPMethod.DELETE, "v2/users/$openId/messages/$messageId", null)
    }

    /**
     * 撤回群聊消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun revokeGroupMessage(openId: String, messageId: String) {
        this.send(HTTPMethod.DELETE, "v2/groups/$openId/messages/$messageId", null)
    }
}