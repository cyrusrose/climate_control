package com.arduino.access.room.domain.model

import android.view.View
import com.arduino.access.room.data.RoomResp

data class Room(
    val id: String,
    val resp: RoomResp,
    var detailsVisibility: Int = View.GONE
) {
    fun clean() {
        detailsVisibility = View.GONE
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Room

        if (id != other.id) return false
        if (resp != other.resp) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + resp.hashCode()
        return result
    }


}