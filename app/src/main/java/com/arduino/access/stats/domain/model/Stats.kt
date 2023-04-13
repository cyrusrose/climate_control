package com.arduino.access.stats.domain.model

import android.net.Uri

data class Stats(
    val id: String,
    val name: String,
    val description: String,
    val pattern: String,
    val uri: String,
    val minValue: Float? = null
)

data class Values(val value: Float)

