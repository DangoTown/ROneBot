/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.metadata

import cn.rtast.rob.common.annotations.ExcludeField
import cn.rtast.rob.util.ob.OneBotAction
import com.google.gson.annotations.SerializedName

data class ConnectEvent(
    @ExcludeField
    var action: OneBotAction,
    @SerializedName("self_id")
    var selfId: String,
    val time: Long,
)