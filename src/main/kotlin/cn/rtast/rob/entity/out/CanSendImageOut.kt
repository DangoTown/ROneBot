/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.enums.MessageEchoType

internal data class CanSendImageOut(
    val action: String = "can_send_image",
    val echo: MessageEchoType = MessageEchoType.CanSendImage
)