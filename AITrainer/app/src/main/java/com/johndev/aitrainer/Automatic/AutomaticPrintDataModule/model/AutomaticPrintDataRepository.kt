package com.johndev.aitrainer.Iterations.IterationsPrintDataModule.model

import com.johndev.aitrainer.common.entities.ResultsAutomaticJSON
import com.johndev.aitrainer.common.entities.ResultsJSON

class AutomaticPrintDataRepository {

    private val printDataMemory = AutomaticPrintDataMemory()

    suspend fun createJSON(name: String, extension: String,resultList: MutableList<ResultsAutomaticJSON>) = printDataMemory.createJSON(name, extension, resultList)

    suspend fun createTXT(name: String, extension: String,resultList: MutableList<ResultsAutomaticJSON>) = printDataMemory.createTXT(name, extension, resultList)

    suspend fun createCSV(name: String, extension: String,resultList: MutableList<ResultsAutomaticJSON>) = printDataMemory.createCSV(name, extension, resultList)

}