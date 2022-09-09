package com.johndev.aitrainer.common.entities

import com.google.gson.annotations.SerializedName

data class ResultsJSON(
    @SerializedName("id")
    val id: Int,
    @SerializedName("w")
    val w: Double,
    @SerializedName("jw")
    val jw: Double)
