package com.arduino.access.room.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.arduino.access.BuildConfig
import com.arduino.access.R
import com.arduino.access.databinding.FragmentFirstBinding
import com.arduino.access.room.domain.model.Room
import com.arduino.access.utils.DEBUG
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.it.access.util.Resource
import com.it.access.util.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FirstFragment : Fragment() {
    private lateinit var ui: FragmentFirstBinding
    private val vm: RoomVm by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentFirstBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Firebase.storage.reference.child("fiber_cable.jpg").downloadUrl.addOnSuccessListener {
//            Log.d(DEBUG, "d" + it.toString())
//        }.addOnFailureListener {
//            Log.d(DEBUG, "d" + it.toString())
//        }

        applyInsets()
        setUpAdapter()
        displayErrors()
    }

    private fun displayErrors() {
        viewLifecycleOwner.collectLatestLifecycleFlow(vm.error) {
            Snackbar.make(ui.root, it.asString(requireContext()), Snackbar.LENGTH_SHORT)
            .show()
        }
    }

    private fun setUpAdapter() {
        val cardAdapter = RoomsAdapter()

        cardAdapter.setClick {
            val act = FirstFragmentDirections.actionFirstFragmentToStatsFragment(
                it.id,
                it.resp.name
            )
            findNavController().navigate(act)
        }

        ui.rv.apply {
            adapter = cardAdapter
        }

        viewLifecycleOwner.collectLatestLifecycleFlow(vm.rooms) {
            if (it is Resource.Success) {
                ui.progress.isVisible = false
                ui.rv.isVisible = true
                cardAdapter.submitList(it.data)
            } else {
                ui.progress.isVisible = true
                ui.rv.isVisible = false
            }
        }

        ui.rv.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(-1)) {
                    ui.fab.extend()
                } else if (dy > 10 && ui.fab.isExtended) {
                    ui.fab.shrink()
                } else if (dy < -10 && !ui.fab.isExtended) {
                    ui.fab.extend()
                }
            }
        })
    }

    private fun applyInsets() {
//        val minimumHeight = ui.toolbar.minimumHeight
//        Insetter.builder().setOnApplyInsetsListener { view, windowInsets, initialState ->
//            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
//
//            view.minimumHeight = minimumHeight + insets.top
//        }
//            .applyToView(ui.toolbar)


        ui.rv.applyInsetter {
            type(navigationBars = true) {
                padding(bottom = true)
            }
        }

        ui.fab.applyInsetter {
            type(navigationBars = true) {
                margin(bottom = true)
            }
        }
    }
}