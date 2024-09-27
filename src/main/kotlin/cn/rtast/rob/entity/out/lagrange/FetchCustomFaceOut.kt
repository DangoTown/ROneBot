/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */


package cn.rtast.rob.entity.out.lagrange

import cn.rtast.rob.enums.MessageEchoType

internal data class FetchCustomFaceOut(
    val action: String = "fetch_custom_face",
    val echo: MessageEchoType = MessageEchoType.FetchCustomFace
)