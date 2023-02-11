package com.arduino.access.view.ui.stats

import androidx.recyclerview.widget.DiffUtil
import com.arduino.access.model.Stats

class StatsDiff: DiffUtil.ItemCallback<Stats>() {
    override fun areItemsTheSame(oldItem: Stats, newItem: Stats) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Stats, newItem: Stats) =
        oldItem == newItem
}
