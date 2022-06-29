package com.johndev.aitrainer.Models

import com.google.gson.annotations.SerializedName

data class ResultsJSON(
    @SerializedName("id")
    val id: Int,
    @SerializedName("w")
    val w: Double,
    @SerializedName("jw")
    val jw: Double)
