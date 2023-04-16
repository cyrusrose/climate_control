package com.arduino.access.stats.domain.model

import com.arduino.access.stats.data.StatsResp

data class Stats(
    val id: String? = null,
    val resp: StatsResp
)

data class Values(val value: Float)

