package com.arduino.access.stats.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arduino.access.room.data.RoomRep
import com.arduino.access.stats.data.StatsRep
import com.arduino.access.utils.DEBUG
import com.it.access.util.Resource
import com.it.access.util.UiText
import com.it.access.util.handler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class StatsVm @Inject constructor(
    private val rep: StatsRep
) : ViewModel() {
    private val _error = MutableSharedFlow<UiText>()
    val error = _error.asSharedFlow()

    private val handler = handler(_error)
    private val scope = viewModelScope + handler

    private val _roomName = MutableStateFlow<String?>(null)

    val stats = _roomName.filterNotNull().flatMapLatest { name ->
        Log.d(DEBUG, "name $name")
        rep.getStats(name).transform {
            if (it is Resource.Error) _error.emit(it.message)
            else emit(it)
        }
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Resource.Loading()
    )

    fun setRoomName(name: String) {
        _roomName.update {
            name
        }
    }
}