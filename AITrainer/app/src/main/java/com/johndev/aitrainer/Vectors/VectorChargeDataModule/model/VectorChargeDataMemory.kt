package com.johndev.aitrainer.Vectors.VectorChargeDataModule.model

import android.content.Context
import android.net.Uri
import com.johndev.aitrainer.Vectors.VectorChargeDataModule.view.VectorDatasetsFragment
import com.johndev.aitrainer.common.entities.ChargeVectorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class VectorChargeDataMemory {

    suspend fun readTextFromUri(uri: Uri, context: Context): MutableList<MutableList<Double>> {
        var listMultiLines: MutableList<MutableList<Double>> = mutableListOf()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                var i = 0
                val counterValues = 0
                listMultiLines = mutableListOf()
                while (line != null) {
                    val lineDataset: MutableList<Double> = mutableListOf()
                    val textElements = line.toString().split(",")
                    textElements.forEach {
                        lineDataset.add(it.trim().toDouble())
                    }
                    listMultiLines.add(lineDataset)
                    i++
                    line = reader.readLine()
                }
            }
        }
        return listMultiLines
    }

    suspend fun sortArrays(dataset: MutableList<MutableList<Double>>): ChargeVectorData {
        val arrayX = Array(dataset.size) { DoubleArray(dataset[0].size - 1) }
        val arrayY = Array(dataset.size) { DoubleArray(1) }
        val arrayW = Array(arrayX[0].size) { DoubleArray(1) }

        //  Crear copias del dataset
        val datasetX: MutableList<MutableList<Double>> = mutableListOf()
        dataset.forEach {
            datasetX.add(it)
        }
        //  Export Array Y
        var indexArray = 0
        datasetX.forEach {
            val index  = it.size - 1
            val listY: MutableList<Double> = mutableListOf()
            listY.add(it[index])
            arrayY[indexArray] = listY.toDoubleArray()
            indexArray++
        }
        //  Export Array X
        indexArray = 0
        datasetX.forEach {
            val x: MutableList<Double> = mutableListOf()
            it.removeLast()
            it.forEach {
                x.add(it)
            }
            arrayX[indexArray] = x.toDoubleArray()
            indexArray++
        }
        indexArray = 0
        arrayW.forEach {
            it.forEach {
                val listW: MutableList<Double> = mutableListOf(0.0)
                arrayW[indexArray] = listW.toDoubleArray()
            }
            indexArray++
        }
        return ChargeVectorData(arrayX, arrayY, arrayW)
    }

}