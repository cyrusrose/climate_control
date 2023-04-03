package com.arduino.access.room.domain.model

import android.net.Uri
import android.view.View

data class Room(
    val id: String,
    val name: String,
    val details: String,
    val uri: Uri,
    var detailsVisibility: Int = View.GONE
) {
    fun clean() {
        detailsVisibility = View.GONE
    }
}