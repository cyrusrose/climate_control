package com.arduino.access.view.ui.stats

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arduino.access.R
import com.arduino.access.databinding.StatsItemBinding
import com.arduino.access.model.Stats
import com.arduino.access.model.Values

class StatsAdapter(private val context: Context): ListAdapter<Stats, StatsAdapter.StatsViewHolder>(
    StatsDiff()
) {
    private var _click: Click? = null
    fun setClick(click: Click) {
        _click = click
    }

    fun interface Click {
        fun onClick(item: Stats)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        return StatsViewHolder(parent, _click)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StatsViewHolder(
        private val ui: StatsItemBinding, private val click: Click?
    ) : RecyclerView.ViewHolder(ui.root) {

        fun bindPayload(mValues: Values) {
            ui.front.apply {
                values = mValues
                executePendingBindings()
            }
        }

        fun bind(
            mStats: Stats
        ) {
            val flipOut = AnimatorInflater.loadAnimator(context, R.animator.flip_out) as AnimatorSet
            val flipIn = AnimatorInflater.loadAnimator(context, R.animator.flip_in) as AnimatorSet


            ui.front.apply {
                stats = mStats
                executePendingBindings()

                card.setOnClickListener {
                    click?.onClick(mStats)
                }
            }

            ui.back.apply {
                stats = mStats
                executePendingBindings()
            }
        }

        constructor (parent: ViewGroup, click: Click?) : this(
            StatsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            click
        )
    }
}





