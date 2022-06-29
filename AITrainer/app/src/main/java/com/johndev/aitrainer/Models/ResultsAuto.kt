package com.johndev.aitrainer.Models

import com.google.gson.annotations.SerializedName

data class ResultsAuto(
    @SerializedName("ID")
    val ID: Int,
    @SerializedName("W0")
    val W0: Double,
    @SerializedName("W1")
    val W1: Double,
    @SerializedName("J")
    val J: Double)
