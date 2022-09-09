package com.johndev.aitrainer.Vectors.VectorOperationsModule.model

import com.johndev.aitrainer.common.entities.PrintDataVectores
import com.johndev.aitrainer.common.entities.VectorResults
import com.johndev.aitrainer.dataAccess.OperationsWithArrays

class VectorOperations {

    suspend fun vectorCalculatios(umbral: Double, arrayXz: Array<DoubleArray>, arrayYz: Array<DoubleArray>, arrayWz: Array<DoubleArray> ): VectorResults {
        val printData: MutableList<PrintDataVectores> = mutableListOf()
        var arrayW = arrayWz
        var arrayUpdateW = Array(arrayXz[0].size) { DoubleArray(1) }
        val alpha = 0.002
        var valueJ = 0.0
        var index = 0
        var magnitude: Double
        val operationsWithArrays = OperationsWithArrays()
        println("Array X --> \n${operationsWithArrays.printArray(arrayXz)}")
        println("Array Y --> \n${operationsWithArrays.printArray(arrayYz)}")
        println("Array W --> \n${operationsWithArrays.printArray(arrayW)}")
        do {
            //  Paso 0 -------------------------------------------------------------------------
            //titleSteps("Paso 0")
            println("Matriz A " + arrayXz.size + "x" + arrayXz[0].size)
            println(operationsWithArrays.printArray(arrayXz))
            println("Matriz B " + arrayW.size + "x"+ arrayW[0].size)
            println(operationsWithArrays.printArray(arrayW))
            //  Paso 1 -------------------------------------------------------------------------
            //titleSteps("Paso 1")
            val arrayRes = operationsWithArrays.crossProductArray(arrayXz, arrayW)
            println("Matriz A " + arrayRes.size + "x"+ arrayRes[0].size)
            println(operationsWithArrays.printArray(arrayRes))
            //  Calculate R
            var average = 0.0
            var indexR = 0
            arrayRes.forEach { internArray ->
                internArray.forEach {
                    average += it
                    indexR++
                }
            }
            average /= indexR
            val valueR = operationsWithArrays.getRVectorial(arrayYz, arrayRes, average)
            //  Paso 2 -------------------------------------------------------------------------
            //titleSteps("Paso 2")
            val resSubtract = operationsWithArrays.subtractArray(arrayRes, arrayYz)
            println(operationsWithArrays.printArray(resSubtract))
            //  Paso 3 -------------------------------------------------------------------------
            //titleSteps("Paso 3")
            val transposeArray = operationsWithArrays.transposeArray(arrayXz)
            println(operationsWithArrays.printArray(transposeArray))
            //  Paso 4 -------------------------------------------------------------------------
            //titleSteps("Paso 4")
            val stepFour = operationsWithArrays.crossProductArray(transposeArray, resSubtract)
            println(operationsWithArrays.printArray(stepFour))
            //  Paso 5 -------------------------------------------------------------------------
            //titleSteps("Paso 5")
            val j = (1 / arrayXz.size.toString().toDouble())
            val multiValue = alpha * (j)
            val stepFive = operationsWithArrays.multiValueArray(multiValue, stepFour)
            println(operationsWithArrays.printArray(stepFive))
            //  Paso 6 -------------------------------------------------------------------------
            //titleSteps("Paso 6 - Resultado De Una Epoca")
            arrayUpdateW = operationsWithArrays.subtractArray(arrayW, stepFive)
            println(operationsWithArrays.printArray(arrayUpdateW))
            //  Paso 7 -------------------------------------------------------------------------
            //titleSteps("Paso 7")
            val newJ = operationsWithArrays.getJ(arrayXz, arrayYz, arrayW)
            println(operationsWithArrays.printArray(newJ))
            newJ.forEach {
                valueJ = it[0]
            }
            arrayW = arrayUpdateW
            println("---------- Iteration $index ----------")
            val getStop = operationsWithArrays.crossProductArray(operationsWithArrays.transposeArray(
                arrayXz
            ), operationsWithArrays.subtractArray(
                operationsWithArrays.crossProductArray(
                    arrayXz,
                    arrayW
                ), arrayYz
            ))
            println(operationsWithArrays.printArray(getStop))
            magnitude = operationsWithArrays.getMagnitude(getStop)
            println("Magnitude --> $magnitude")
            //  Index, Ws, J, R2
            printData.add(PrintDataVectores(index, valueJ, valueR))
            //chartVectorData.add(ChartVectorData(index, valueJ, arrayW))
            index++
        } while (umbral < magnitude)
        return VectorResults(printData, arrayUpdateW)
    }

}