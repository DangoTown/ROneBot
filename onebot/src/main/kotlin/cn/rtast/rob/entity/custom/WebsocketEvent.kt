/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/31
 */


package cn.rtast.rob.entity.custom

import cn.rtast.rob.common.annotations.ExcludeField
import cn.rtast.rob.util.ob.OneBotAction

data class CloseEvent(
    @ExcludeField
    val action: OneBotAction,
    val code: Int,
    val reason: String,
    val remote: Boolean
)

data class ErrorEvent(
    @ExcludeField
    val action: OneBotAction,
    val exception: Exception,
)