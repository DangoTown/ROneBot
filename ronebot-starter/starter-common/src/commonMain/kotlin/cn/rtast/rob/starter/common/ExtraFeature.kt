/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/29
 */

package cn.rtast.rob.starter.common

public enum class ExtraFeature(
    public val featureString: String,
    public val featureName: String,
    public val replacement: String,
    public val disableReason: String
) {
    Permission(
        "permission",
        "权限控制",
        "implementation(\"cn.rtast.rob:ronebot-permission:{{ROB_VERSION}}\")",
        "该模块已合并至ronebot-common模块内无需手动引入"
    );

    public companion object {
        public fun fromList(str: String): ExtraFeature? {
            return when (str) {
                Permission.featureString -> Permission
                else -> null
            }
        }
    }
}