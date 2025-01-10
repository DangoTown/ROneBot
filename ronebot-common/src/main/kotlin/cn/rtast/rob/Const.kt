/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/7
 */


package cn.rtast.rob

import cn.rtast.rob.util.ExcludeStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gson: Gson = GsonBuilder()
    .disableHtmlEscaping()
    .setPrettyPrinting()
    .addSerializationExclusionStrategy(ExcludeStrategy())
    .addDeserializationExclusionStrategy(ExcludeStrategy())
    .create()