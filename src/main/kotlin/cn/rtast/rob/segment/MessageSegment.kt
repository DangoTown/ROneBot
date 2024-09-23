/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.segment

import cn.rtast.rob.enums.ArrayMessageType
import cn.rtast.rob.enums.ContactType

data class PlainText(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.text
) : BaseSegment() {
    data class Data(val text: String)
}

data class Face(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.face
) : BaseSegment() {
    data class Data(val id: String)
}

data class Image(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.image
) : BaseSegment() {
    data class Data(val file: String)
}

data class Record(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.record
) : BaseSegment() {
    data class Data(val file: String)
}

data class Video(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.video
) : BaseSegment() {
    data class Data(val file: String)
}

data class AT(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.at
) : BaseSegment() {
    data class Data(val qq: String)
}

data class Poke(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.poke
) : BaseSegment() {
    data class Data(val type: String, val id: String)
}

data class Share(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.share
) : BaseSegment() {
    data class Data(
        val url: String,
        val title: String,
        val content: String? = null,
        val image: String? = null
    )
}

data class Contact(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.contact
) : BaseSegment() {
    data class Data(val type: ContactType, val id: String)
}

data class Location(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.location
) : BaseSegment() {
    data class Data(
        val lat: String,
        val lon: String,
        val title: String? = null,
        val content: String? = null,
    )
}

data class MusicShare(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.music
) : BaseSegment() {
    data class Data(val type: String, val id: String)
}

data class CustomMusicShare(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.music
) : BaseSegment() {
    data class Data(
        val url: String,
        val audio: String,
        val title: String? = null,
        val content: String? = null,
        val image: String? = null,
        val type: String = "custom",
    )
}

data class Reply(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.reply
) : BaseSegment() {
    data class Data(val id: String)
}

data class XML(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.xml
) : BaseSegment() {
    data class Data(val data: String)
}

data class JSON(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.json
) : BaseSegment() {
    data class Data(val data: String)
}

data class RPS(val type: ArrayMessageType = ArrayMessageType.rps) : BaseSegment()
data class DICE(val type: ArrayMessageType = ArrayMessageType.dice) : BaseSegment()
data class Shake(val type: ArrayMessageType = ArrayMessageType.shake) : BaseSegment()

data class Node(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.node
) : BaseSegment() {
    data class Data(
        val name: String,
        val uin: String,
        val content: List<BaseSegment>
    )
}