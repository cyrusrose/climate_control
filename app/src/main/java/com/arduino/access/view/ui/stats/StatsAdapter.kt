package com.arduino.access.view.ui.stats

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arduino.access.R
import com.arduino.access.databinding.StatsItemBinding
import com.arduino.access.model.Stats
import com.arduino.access.model.Values
import com.arduino.access.view.ui.MyApplication

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

    override fun onViewDetachedFromWindow(holder: StatsViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clean()
    }

    inner class StatsViewHolder(
        private val ui: StatsItemBinding,
        private val click: Click?
    ) : RecyclerView.ViewHolder(ui.root) {
        private var isFlipFinished = true

        fun clean() {
            isFlipFinished = true
            ui.viewFront.alpha = 1F
            ui.viewBack.alpha = 1F
            ui.viewFront.rotationY = 0F
            ui.viewBack.rotationY = 0F

            ui.viewFront.isVisible = true
            ui.viewBack.isVisible = false
        }

        fun bindPayload(mValues: Values) {
            ui.front.apply {
                values = mValues
                executePendingBindings()
            }
        }

        fun bind(
            mStats: Stats
        ) {
            ui.front.apply {
                stats = mStats
                executePendingBindings()
            }

            ui.back.apply {
                stats = mStats
                executePendingBindings()
            }

            ui.statsContainer.apply {
                setOnClickListener {
                    if (isFlipFinished) {
                        isFlipFinished = false
                        val visibleView = if (ui.viewFront.isVisible) ui.viewFront else ui.viewBack
                        val invisibleView = if (!ui.viewFront.isVisible) ui.viewFront else ui.viewBack
                        flipCard(visibleView, invisibleView)
                    }

                    click?.onClick(mStats)
                }
            }
        }

        private fun flipCard(visibleView: View, invisibleView: View) {
            try {
                invisibleView.isVisible = true
                val scale = context.resources.displayMetrics.density
                val cameraDist = 12_000 * scale
                visibleView.cameraDistance = cameraDist
                invisibleView.cameraDistance = cameraDist

                val flipOutAnimatorSet =
                    AnimatorInflater.loadAnimator(
                        context,
                        R.animator.flip_out
                    ) as AnimatorSet
                flipOutAnimatorSet.setTarget(visibleView)

                val flipInAnimationSet =
                    AnimatorInflater.loadAnimator(
                        context,
                        R.animator.flip_in
                    ) as AnimatorSet
                flipInAnimationSet.setTarget(invisibleView)

                flipOutAnimatorSet.start()
                flipInAnimationSet.start()

                flipInAnimationSet.doOnEnd {
                    visibleView.isVisible = false
                    isFlipFinished = true
                }
            } catch (e: Exception) {
                Log.d(MyApplication.DEBUG, e.message ?: "No")
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





