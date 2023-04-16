package com.arduino.access.stats.data

data class StatsResp(
    val name: String,
    val pattern: String,
    val url: String,
    val minValue: Float
) {
    constructor(): this("", "", "", 0f)
}