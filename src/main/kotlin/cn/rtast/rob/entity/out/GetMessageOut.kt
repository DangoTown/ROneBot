/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out

import com.google.gson.annotations.SerializedName

data class GetMessageOut(
    val action: String = "get_msg",
    val params: Params,
) {
    data class Params(
        @SerializedName("message_id")
        val groupId: Long,
    )
}