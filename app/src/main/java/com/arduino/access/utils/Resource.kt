package com.it.access.util

sealed class Resource<T>(open val data: T? = null, open val message: UiText? = null) {
    class Loading<T>: Resource<T>()
    class Success<T>(data: T): Resource<T>(data) {
        override val data get() = super.data!!
    }
    class Error<T>(message: UiText, data: T? = null): Resource<T>(data, message) {
        override val message get() = super.message!!

        constructor(message: String, data: T? = null) : this(UiText.DynamicString(message), data)
    }
}