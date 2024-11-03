/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.entity.out.lagrange.set

import java.util.UUID

internal data class SetBotAvatarOut(
    val params: Params,
    val action: String = "set_qq_avatar",
    val echo: UUID
) {
    data class Params(
        val file: String
    )
}

internal data class SetBotAvatar(
    val status: String
)