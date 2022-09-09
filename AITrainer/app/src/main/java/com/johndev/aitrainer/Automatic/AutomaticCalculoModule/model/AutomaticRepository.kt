package com.johndev.aitrainer.Automatic.AutomaticCalculoModule.model

import com.johndev.aitrainer.common.entities.DatasetComplete
import com.johndev.aitrainer.common.entities.Automatic

class AutomaticRepository {

    private var database = AutomaticDatabase()

    suspend fun automaticMethodForDiferences(
        umbral: Double,
        dataset: DatasetComplete
    ): MutableList<Automatic> = database.automaticMethodForDiferences(umbral, dataset)

}