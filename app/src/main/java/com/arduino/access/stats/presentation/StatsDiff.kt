package com.arduino.access.stats.presentation

import androidx.recyclerview.widget.DiffUtil
import com.arduino.access.stats.domain.model.Stats

class StatsDiff: DiffUtil.ItemCallback<Stats>() {
    override fun areItemsTheSame(oldItem: Stats, newItem: Stats) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Stats, newItem: Stats) =
        oldItem == newItem
}
