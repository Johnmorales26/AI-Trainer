package com.johndev.aitrainer.Ranges.RangesMethodModule.model

import com.johndev.aitrainer.common.entities.DatasetComplete

class RangesRepository {

    private val database = RangesDatabase()

    suspend fun methodRanges(w: Double, b: Double, rango1: Double, rango2: Double, dataset: DatasetComplete) = database.methodRanges(w, b, rango1, rango2, dataset)

}