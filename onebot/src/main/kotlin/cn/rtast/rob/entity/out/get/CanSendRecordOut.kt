/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.entity.out.get

import java.util.UUID

internal data class CanSendRecordOut(
    val echo: UUID,
    val action: String = "can_send_record",
)