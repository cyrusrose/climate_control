package com.arduino.access.view.ui.room

import android.animation.LayoutTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import androidx.transition.Visibility
import com.arduino.access.databinding.RoomItemBinding
import com.arduino.access.model.Room

class RoomsAdapter: ListAdapter<Room, RoomsAdapter.RoomViewHolder>(RoomDiff()) {
    private var _click: Click? = null
    fun setClick(click: Click) {
        _click = click
    }

    fun interface Click {
        fun onClick(item: Room)
    }

    override fun onViewDetachedFromWindow(holder: RoomViewHolder) {
        super.onViewDetachedFromWindow(holder)

        getItem(holder.adapterPosition).clean()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(parent, _click)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: RoomViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else if (payloads[0] is Boolean)
            holder.bindPayload(getItem(position))
    }

    inner class RoomViewHolder(
        val ui: RoomItemBinding, private val click: Click?
    ) : RecyclerView.ViewHolder(ui.root) {

        fun bindPayload(item: Room) {
            ui.apply {
                ui.payload = item.detailsVisibility
                executePendingBindings()
            }
        }

        fun bind(
            item: Room
        ) {
            ui.apply {
                room = item
                payload = item.detailsVisibility
                executePendingBindings()

                info.setOnClickListener {
                    val v = if (ui.details.visibility == View.GONE) View.VISIBLE else View.GONE
                    item.detailsVisibility = v
                    notifyItemChanged(adapterPosition, true)
                }

                card.setOnClickListener {
                    click?.onClick(item)
                }
            }
        }


        constructor (parent: ViewGroup, click: Click?) :
            this(
                RoomItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                click
            )
    }
}





