package com.arduino.access.model

import android.net.Uri

data class Stats(
    val id: String,
    val name: String,
    val description: String,
    val pattern: String,
    val uri: Uri,
    val minValue: Float? = null
)

data class Values(val value: Float)

