package com.arduino.access.view.ui.room

import androidx.recyclerview.widget.DiffUtil
import com.arduino.access.model.Room

class RoomDiff: DiffUtil.ItemCallback<Room>() {
    override fun areItemsTheSame(oldItem: Room, newItem: Room) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Room, newItem: Room) =
        oldItem == newItem
}
