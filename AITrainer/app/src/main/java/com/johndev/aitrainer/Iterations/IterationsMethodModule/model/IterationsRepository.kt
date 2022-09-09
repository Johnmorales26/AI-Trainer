package com.johndev.aitrainer.Iterations.IterationsMethodModule.model

import com.johndev.aitrainer.common.entities.DatasetComplete

class IterationsRepository {

    private val database = IterationsDatabase()

    suspend fun methodIterator(valueB: Double, valueW: Double, iterations: Double, dataset: DatasetComplete) = database.methodIterator(valueB, valueW, iterations, dataset)

}