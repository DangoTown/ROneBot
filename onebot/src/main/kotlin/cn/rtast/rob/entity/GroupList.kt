/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

data class GroupList(
    val data: List<Data>
) {
    data class Data(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("group_name")
        val groupName: String,
        @SerializedName("member_count")
        val memberCount: Int,
        @SerializedName("max_member_count")
        val maxMemberCount: Int,
    )
}