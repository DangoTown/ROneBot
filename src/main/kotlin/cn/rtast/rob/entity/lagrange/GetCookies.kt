/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.entity.lagrange

data class GetCookies(
    val data: Data
) {
    data class Data(
        val cookies: String
    )
}