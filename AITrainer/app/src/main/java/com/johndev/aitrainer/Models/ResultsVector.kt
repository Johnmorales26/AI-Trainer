package com.johndev.aitrainer.Models

import com.google.gson.annotations.SerializedName

data class ResultsVector(
    @SerializedName("ID")
    val ID: Int,
    @SerializedName("ListWs")
    val ListWs: Array<DoubleArray>,
    @SerializedName("J")
    val J: Double)
