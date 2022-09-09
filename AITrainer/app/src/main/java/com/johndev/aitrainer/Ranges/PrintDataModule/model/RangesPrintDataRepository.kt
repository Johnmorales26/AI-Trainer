package com.johndev.aitrainer.Iterations.IterationsPrintDataModule.model

import com.johndev.aitrainer.common.entities.ResultsJSON

class RangesPrintDataRepository {

    private val printDataMemory = RangesPrintDataMemory()

    suspend fun createJSON(name: String, extension: String,resultList: MutableList<ResultsJSON>) = printDataMemory.createJSON(name, extension, resultList)

    suspend fun createTXT(name: String, extension: String,resultList: MutableList<ResultsJSON>) = printDataMemory.createTXT(name, extension, resultList)

    suspend fun createCSV(name: String, extension: String,resultList: MutableList<ResultsJSON>) = printDataMemory.createCSV(name, extension, resultList)

}