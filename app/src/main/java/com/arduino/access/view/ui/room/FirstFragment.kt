package com.arduino.access.view.ui.room

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.arduino.access.BuildConfig
import com.arduino.access.R
import com.arduino.access.databinding.FragmentFirstBinding
import com.arduino.access.model.Room
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirstFragment : Fragment() {
    private  lateinit var ui: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentFirstBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyInsets()

        setUpAdapter()
    }

    private fun setUpAdapter() {
        val cardAdapter = RoomsAdapter()
        cardAdapter.setClick {
            val act =
                FirstFragmentDirections.actionFirstFragmentToStatsFragment(
                    it.name
                )
            findNavController().navigate(act)
        }


        ui.rv.apply {
            adapter = cardAdapter

            cardAdapter.apply {
                lifecycleScope.launch {
                    submitList(
                        withContext(coroutineContext + Dispatchers.Default) {
                            (1..12).map { Room(
                                id = it.toString(),
                                name ="Room $it",
                                uri = Uri.parse("android.resource://${BuildConfig.APPLICATION_ID}/${R.drawable.fiber_cable}"),
                                details = "Something pretty long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long"
                            )}
                        }
                    )
                }
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