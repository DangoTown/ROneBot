/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class CallAPIOut(
    val action: String,
    val params: Map<String, Any>,
    val echo: MessageEchoType = MessageEchoType.CallCustomApi
)