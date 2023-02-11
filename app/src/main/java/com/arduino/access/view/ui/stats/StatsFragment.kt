package com.arduino.access.view.ui.stats

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arduino.access.BuildConfig
import com.arduino.access.R
import com.arduino.access.databinding.FragmentStatsBinding
import com.arduino.access.model.Stats
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatsFragment : Fragment() {
    private lateinit var ui: FragmentStatsBinding
    private val args: StatsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentStatsBinding.inflate(inflater, container, false)
        ui.title = args.room
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyInsets()

        setUpToolBar()
        setUpAdapter()
    }

    private fun setUpAdapter() {
        val cardAdapter = StatsAdapter(requireContext())
        cardAdapter.setClick {

        }


        ui.rv.apply {
            adapter = cardAdapter

            cardAdapter.apply {
                lifecycleScope.launch {
                    submitList(
                        withContext(coroutineContext + Dispatchers.Default) {
                            (1..20).map { Stats(
                                id = it.toString(),
                                name = "Temperature N$it",
                                description = "Good temperature",
                                pattern = "%.2f C",
                                uri = Uri.parse("android.resource://${BuildConfig.APPLICATION_ID}/${R.drawable.fiber_cable}")
                            )}
                        }
                    )
                }
            }
        }
    }

    private fun setUpToolBar() {
        ui.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.action_settings -> true
                else -> false
            }
        }

        ui.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        ui.ab.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when(state) {
                    State.EXPANDED -> {
                        ui.toolbar.menu.findItem(R.id.report).isVisible = false
                    }
                    else -> {
                        ui.toolbar.menu.findItem(R.id.report).isVisible = true
                    }
                }
            }
        })
    }

    private fun applyInsets() {
//        ui.toolbar.applyInsetter {
//            type(statusBars = true) {
//                margin(top = true)
//            }
//            consume(true)
//        }
    }
}