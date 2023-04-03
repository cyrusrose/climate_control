package com.arduino.access.room.presentation

import androidx.recyclerview.widget.DiffUtil
import com.arduino.access.room.domain.model.Room

class RoomDiff: DiffUtil.ItemCallback<Room>() {
    override fun areItemsTheSame(oldItem: Room, newItem: Room) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Room, newItem: Room) =
        oldItem == newItem
}
