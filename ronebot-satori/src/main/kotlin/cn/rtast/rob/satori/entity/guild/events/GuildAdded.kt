/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/11
 */


package cn.rtast.rob.satori.entity.guild.events

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.satori.entity.guild.inbound.GetGuild
import cn.rtast.rob.satori.satori.SatoriAction
import com.google.gson.annotations.SerializedName

public data class GuildAdded(
    val op: Int,
    val body: GuildAdded
) {
    public data class GuildAdded(
        @ExcludeField
        var action: SatoriAction,
        val id: Int,
        val type: String,
        @SerializedName("self_id")
        val selfId: String,
        val platform: String,
        val timestamp: Long,
        val guild: GetGuild
    )
}