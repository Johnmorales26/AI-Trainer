package com.johndev.aitrainer.Iterations.IterationsPrintDataModule.model

import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.johndev.aitrainer.Constants.SEPARATOR_CSV
import com.johndev.aitrainer.common.entities.ResultsJSON
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.lang.Exception

class RangesPrintDataMemory() {

    suspend fun createJSON(name: String, extension: String,resultList: MutableList<ResultsJSON>): Boolean {
        val gson = Gson()
        val exportJSON = gson.toJson(resultList)
        Log.i("JSON EXPORT", exportJSON.toString())
        return writeToFile(name,  extension, exportJSON)
    }

    suspend fun createTXT(name: String, extension: String,resultList: MutableList<ResultsJSON>): Boolean {
        var exportTXT = ""
        resultList.forEach {
            exportTXT += "Iteraciones: ${it.id} ---------- W: ${it.w} ---------- JW: ${it.jw}\n"
        }
        return writeToFile(name,  extension, exportTXT)
    }

    suspend fun createCSV(name: String, extension: String,resultList: MutableList<ResultsJSON>): Boolean {
        var exportCSV = ""
        resultList.forEach {
            exportCSV += "${it.id}$SEPARATOR_CSV${it.w}$SEPARATOR_CSV${it.jw}\n"
        }
        return writeToFile(name,  extension, exportCSV)
    }

    private fun writeToFile(name: String, extension: String, exportFile: String?): Boolean {
        return try {
            val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(filePath, "$name$extension".trim())
            if (!file.exists()){
                file.createNewFile()
            }
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.write(exportFile)
            bufferedWriter.close()
            true
        } catch (e: Exception) {
            false
        }
    }

}