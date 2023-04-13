package com.arduino.access.room.data

import android.net.Uri

data class RoomResp(
    val name: String,
    val details: String,
    val url: String
) {
    constructor(): this("", "", "")
}