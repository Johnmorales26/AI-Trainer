package com.johndev.aitrainer.common.entities

import com.google.gson.annotations.SerializedName

data class ResultsAutomaticJSON(
    @SerializedName("id")
    val id: Int,
    @SerializedName("w0")
    val w0: Double,
    @SerializedName("w1")
    val w1: Double,
    @SerializedName("jw")
    val jw: Double)
