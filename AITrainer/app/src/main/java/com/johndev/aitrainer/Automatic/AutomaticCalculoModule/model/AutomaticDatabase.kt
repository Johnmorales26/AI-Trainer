package com.johndev.aitrainer.Automatic.AutomaticCalculoModule.model

import com.johndev.aitrainer.common.entities.DatasetComplete
import com.johndev.aitrainer.dataAccess.NeuronTraining
import com.johndev.aitrainer.common.entities.Automatic
import java.util.*

class AutomaticDatabase {

    private val operations = NeuronTraining()

    suspend fun automaticMethodForDiferences(umbral: Double, database: DatasetComplete): MutableList<Automatic> {
        val results: MutableList<Automatic> = mutableListOf()
        val random = Random()
        var w0 = 0.0//random.nextInt(1..2147483647).toFloat()
        var w1 = 0.0//random.nextInt(1..2147483647).toFloat()
        var j = 0.0
        var x = 0
        var rest: Double
        var magnitude: Double
        var stop: Boolean
        do {
            //  Variables For Cycle Calculation
            val newJ = operations.getJ(w1, w0, database.valuesX, database.valuesY)
            val newW0 = operations.getAproximateW0(w0, w1, database.valuesX, database.valuesY)
            val newW1 = operations.getAproximateW1(w0, w1, database.valuesX, database.valuesY)
            val guess = operations.resolveGuess(database.valuesX, w1, w0)
            //  Calculate Regression Line
            val ssRegresion = operations.getSSRegresion(database.valuesY, guess)
            val ssTotal = operations.getSSTotal(database.valuesY)
            //  Variables To Stop The Loop
            val newMagnitude = operations.getResultingMagnitude(j, newW1)
            val newRest = operations.getDifferenceMagnitude(newW0, newW1)
            val valueR = operations.getRSquared(ssRegresion, ssTotal)
            println("Iteracion: $x")
            println("W0 = $newW0")
            println("W1 = $newW1")
            println("J = $j")

            magnitude = operations.getMagnitude(operations.getAproximateW0Average(w0, w1, database.valuesX, database.valuesY),
                operations.getAproximateW1Average(w0, w1, database.valuesX, database.valuesY))
            stop = umbral!! < magnitude

            w0 = newW0
            w1 = newW1
            j = newJ
            results.add(Automatic(x, w0, w1, j, valueR))
            rest = newRest
            println("Rest = $rest")
            x++
        } while (stop)
        return results
    }

}