package com.johndev.aitrainer.Vectors.VectorPrintDataModule.model

import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.johndev.aitrainer.common.entities.ResultsVector
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class VectorPrintDataMemory {

    private val separatorCSV = ","

    suspend fun createJSON(resultList: MutableList<ResultsVector>, name: String, extension: String): Boolean {
        val gson = Gson()
        val exportJSON = gson.toJson(resultList)
        Log.i("JSON EXPORT", exportJSON.toString())
        return writeToFile(exportJSON, name, extension)
    }

    suspend fun createTXT(resultList: MutableList<ResultsVector>, name: String, extension: String): Boolean {
        var exportTXT = ""
        resultList.forEach {
            var index = 0
            var Ws = ""
            it.ListWs.forEach { arrayW ->
                arrayW.forEach { valueW ->
                    Ws += "-- W$index: $valueW --"
                    index++
                }
            }
            exportTXT += "Epoch: ${it.ID} $Ws J: ${it.J}\n"
        }
        return writeToFile(exportTXT, name, extension)
    }

    suspend fun createCSV(resultList: MutableList<ResultsVector>, name: String, extension: String): Boolean {
        var exportCSV = ""
        resultList.forEach {
            var index = 0
            var Ws = ""
            it.ListWs.forEach { arrayW ->
                arrayW.forEach { valueW ->
                    Ws += "$separatorCSV$valueW"
                    index++
                }
            }
            exportCSV += "${it.ID}${Ws}$separatorCSV${it.J}\n"
        }
        return writeToFile(exportCSV, name, extension)
    }

    private suspend fun writeToFile(exportFile: String?, name: String, extension: String): Boolean {
        var isCompleted = true
        try {
            val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(filePath, "$name$extension".trim())
            if (!file.exists()) {
                file.createNewFile()
            }
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.let {
                it.write(exportFile)
                it.close()
            }
        } catch (e: Exception) {
            isCompleted = false
        }
        return isCompleted
    }

}