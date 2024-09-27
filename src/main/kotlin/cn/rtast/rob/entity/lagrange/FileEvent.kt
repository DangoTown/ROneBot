/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/8
 */


package cn.rtast.rob.entity.lagrange

import cn.rtast.rob.actionable.FileEventActionable
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.net.URI

/**
 * Lagrange.OneBot的拓展Segment解析
 */
data class FileEvent(
    @SerializedName("group_id")
    val groupId: Long?,
    @SerializedName("user_id")
    val userId: Long,
    val file: File,
) : FileEventActionable {
    data class File(
        val id: String,
        val name: String,
        val size: Int,
        val url: String
    )

    override suspend fun saveTo(path: String) {
        this.saveTo(java.io.File(path, file.name))
    }

    /**
     * 分块保存文件
     */
    override suspend fun saveTo(file: java.io.File) {
        println("Saving ${this@FileEvent.file.name} to ${file.path}")
        return withContext(Dispatchers.IO) {
            try {
                val connection = URI(this@FileEvent.file.url).toURL().openConnection()
                connection.inputStream.use { inputStream ->
                    FileOutputStream(file).use { outputStream ->
                        val buffer = ByteArray(4096)
                        var bytesRead: Int
                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                        }
                    }
                }
                println("Successfully saved to ${file.path}")
            } catch (e: Exception) {
                e.printStackTrace()
                println("Failed to save file: ${e.message}")
            }
        }
    }

    override suspend fun readBytes(): ByteArray {
        val url = URI(this@FileEvent.file.url).toURL()
        return withContext(Dispatchers.IO) {
            try {
                url.openStream().use { inputStream ->
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    val buffer = ByteArray(4096)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead)
                    }
                    byteArrayOutputStream.toByteArray()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                byteArrayOf()
            }
        }
    }
}