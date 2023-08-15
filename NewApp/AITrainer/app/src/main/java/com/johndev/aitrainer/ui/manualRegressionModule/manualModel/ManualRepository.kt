package com.johndev.aitrainer.ui.manualRegressionModule.manualModel

import android.content.Context
import android.net.Uri
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class ManualRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun readTextFromUri(uri: Uri): List<List<Double>> = withContext(Dispatchers.IO) {
        val resultList: MutableList<List<Double>> = mutableListOf()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    val lineDataset: MutableList<Double> = mutableListOf()
                    val textElements = line.split(",").map { it.trim() }
                    for (element in textElements) {
                        if (element.isNotEmpty()) {
                            try {
                                lineDataset.add(element.toDouble())
                            } catch (e: NumberFormatException) {
                                Log.i("Error Data", "No se pudo convertir a Double: $element")
                            }
                        }
                    }
                    if (lineDataset.isNotEmpty()) {
                        resultList.add(lineDataset)
                    }
                    line = reader.readLine()
                }
            }
        }
        return@withContext resultList
    }


}