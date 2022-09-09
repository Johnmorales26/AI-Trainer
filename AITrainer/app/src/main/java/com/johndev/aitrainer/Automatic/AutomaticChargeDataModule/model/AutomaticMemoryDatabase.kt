package com.johndev.aitrainer.Iterations.IterationsChargeDataModule.model

import android.content.Context
import android.net.Uri
import com.johndev.aitrainer.common.entities.Dataset
import java.io.BufferedReader
import java.io.InputStreamReader

class AutomaticMemoryDatabase {

    suspend fun readTextFromUri(uri: Uri, context: Context): MutableList<Dataset> {
        val dataset: MutableList<Dataset> = mutableListOf()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                var i = 0
                while (line != null) {
                    val textElements = line.toString().split(",")
                    val valueX = textElements[0].trim().toDouble()
                    val valueY = textElements[1].trim().toDouble()
                    dataset.add(Dataset(valueX, valueY))
                    i++
                    line = reader.readLine()
                }
            }
        }
        return dataset
    }

}