/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.entity.ArrayMessage
import com.google.gson.annotations.SerializedName

internal data class CQCodePrivateMessageOut(
    val action: String = "send_private_msg",
    val params: Params,
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
        val message: String,
    )
}
internal data class ArrayPrivateMessageOut(
    val action: String = "send_private_msg",
    val params: Params,
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
        val message: List<ArrayMessage>,
    )
}
