package com.johndev.aitrainer.Iterations.IterationsMethodModule.model

import com.johndev.aitrainer.dataAccess.NeuronTraining
import com.johndev.aitrainer.common.entities.DatasetComplete
import com.johndev.aitrainer.common.entities.ChargeData
import com.johndev.aitrainer.common.entities.ResultsPerceptron
import com.johndev.aitrainer.format.getTwoDecimals

class IterationsDatabase {

    private val operations = NeuronTraining()

    suspend fun methodIterator(valueB: Double, valueW: Double, iterations: Double, dataset: DatasetComplete): MutableList<ResultsPerceptron> {
        var counter = 1
        var w = valueW
        var derivadaRes: Double
        var costo: Double
        var valueJW: String
        var error: MutableList<Double>
        var guess: MutableList<Double>
        val results = mutableListOf<ResultsPerceptron>()
        val resume = mutableListOf<ChargeData>()
        while (counter <= iterations){
            derivadaRes = operations.resolveDerivative(w, valueB, dataset.valuesX, dataset.valuesY)
            guess = operations.resolveGuess(dataset.valuesX, w, valueB)
            error = operations.resolveError(guess, dataset.valuesY)
            valueJW = getTwoDecimals(operations.resolveCost(w, valueB,
                dataset.valuesX,
                dataset.valuesY
            ))
            costo = operations.resolveCost(w, valueB,
                dataset.valuesX,
                dataset.valuesY
            )
            println("---------- Iteracion #$counter ----------")
            println("El valor de w: ${getTwoDecimals(w)}")
            println("El costo es de: $costo")
            println("El valor de J(w) es igual a: $valueJW")
            println("El valor de la derivada es igual a ${getTwoDecimals(operations.resolveDerivative(w, valueB,
                dataset.valuesX,
                dataset.valuesY
            ))}")
            println("El valor de Guess es igual a: $error")
            println("El valor de Error es igual a: $guess")
            val charge = ChargeData(counter, w, valueJW.toDouble(), costo)
            results.add(
                ResultsPerceptron(counter, w, valueB, iterations.toInt(), valuesX = dataset.valuesX,
                valuesY = dataset.valuesY, valueJW = valueJW.toDouble(), derivada = derivadaRes, error = error,
                guess = guess, costo = costo)
            )
            resume.add(charge)
            w = operations.resolveW(w, derivadaRes)
            counter++
        }
        return results
    }

}