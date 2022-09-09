package com.johndev.aitrainer.Vectors.VectorChargeDataModule.model

import android.content.Context
import android.net.Uri

class VectorChargeDataRepository {

    private var vectorMemory = VectorChargeDataMemory()

    suspend fun readTextFromUri(uri: Uri, context: Context) = vectorMemory.readTextFromUri(uri, context)

    suspend fun sortArrays(dataset: MutableList<MutableList<Double>>) = vectorMemory.sortArrays(dataset)

}