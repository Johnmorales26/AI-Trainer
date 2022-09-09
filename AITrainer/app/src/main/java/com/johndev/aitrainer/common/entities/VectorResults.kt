package com.johndev.aitrainer.common.entities

data class VectorResults(
    val paintResults: MutableList<PrintDataVectores>,
    val wResults: Array<DoubleArray>
    )
