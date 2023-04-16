package com.arduino.access.stats.presentation

import  android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arduino.access.BuildConfig
import com.arduino.access.R
import com.arduino.access.databinding.FragmentStatsBinding
import com.arduino.access.stats.domain.model.Stats
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.it.access.util.Resource
import com.it.access.util.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class StatsFragment : Fragment() {
    private lateinit var ui: FragmentStatsBinding
    private val args: StatsFragmentArgs by navArgs()
    private val vm: StatsVm by viewModels()

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

        displayErrors()
        setUpToolBar()
        setUpAdapter()
    }

    private fun displayErrors() {
        viewLifecycleOwner.collectLatestLifecycleFlow(vm.error) {
            Snackbar.make(ui.root, it.asString(requireContext()), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun setUpAdapter() {
        vm.setRoomName(args.id)

        val cardAdapter = StatsAdapter(requireContext())

        ui.rv.apply {
            adapter = cardAdapter
        }

        viewLifecycleOwner.collectLatestLifecycleFlow(vm.stats) {
            if (it is Resource.Success) {
                ui.progress.isVisible = false
                ui.rv.isVisible = true
                cardAdapter.submitList(it.data)
            } else {
                ui.progress.isVisible = true
                ui.rv.isVisible = false
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