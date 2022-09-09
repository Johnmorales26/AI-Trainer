package com.johndev.aitrainer.Vectors.VectorPrintDataModule.model

import com.johndev.aitrainer.common.entities.ResultsVector

class VectorPrintDataRepository {

    private val printDataMemory = VectorPrintDataMemory()

    suspend fun createJSON(name: String, extension: String,resultList: MutableList<ResultsVector>) = printDataMemory.createJSON(resultList, name, extension)

    suspend fun createTXT(name: String, extension: String,resultList: MutableList<ResultsVector>) = printDataMemory.createTXT(resultList, name, extension)

    suspend fun createCSV(name: String, extension: String,resultList: MutableList<ResultsVector>) = printDataMemory.createCSV(resultList, name, extension)

}