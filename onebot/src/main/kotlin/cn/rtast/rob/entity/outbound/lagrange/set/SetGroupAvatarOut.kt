/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.entity.outbound.lagrange.set

import java.util.UUID

internal data class SetGroupAvatarOut(
    val params: Params,
    val action: String = "set_group_portrait",
    val echo: UUID
) {
    data class Params(
        val file: String
    )
}

internal data class SetGroupAvatar(val status: String)