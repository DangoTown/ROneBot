/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.frontend

import io.ktor.client.*

public val client: HttpClient = HttpClient()

public const val defaultROBVersion: String = "3.0.0"
public const val defaultGradleVersion: String = "8.13"
public const val defaultKotlinVersion: String = "2.1.20"
public const val JVM_ONLY_LINTER_VERSION: String = "0.1.2"