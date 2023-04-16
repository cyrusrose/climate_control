package com.it.access.util

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.arduino.access.room.domain.model.Room
import com.arduino.access.utils.DEBUG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> LifecycleOwner.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T)-> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun <T> LifecycleOwner.collectLifecycleFlow(flow: Flow<T>, collect: suspend (T)-> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }
}

fun ViewModel.handler(_error: MutableSharedFlow<UiText>) =
    CoroutineExceptionHandler { _, throwable ->
    viewModelScope.launch {
        throwable.message?.let {
            _error.emit(UiText.DynamicString(it))
        }
    }
    Log.d(DEBUG, "Error: " + throwable.message)
}
