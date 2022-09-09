package com.johndev.aitrainer.Vectors.VectorOperationsModule.model

class VectorRepository {

    private val operations = VectorOperations()

    suspend fun vectorCalculatios(umbral: Double, arrayXz: Array<DoubleArray>, arrayYz: Array<DoubleArray>
                                  , arrayWz: Array<DoubleArray>) = operations.vectorCalculatios(umbral, arrayXz, arrayYz, arrayWz)

}