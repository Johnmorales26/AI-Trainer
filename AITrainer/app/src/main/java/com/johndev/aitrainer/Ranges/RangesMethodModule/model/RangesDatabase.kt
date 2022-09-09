package com.johndev.aitrainer.Ranges.RangesMethodModule.model

import com.johndev.aitrainer.common.entities.DatasetComplete
import com.johndev.aitrainer.dataAccess.NeuronTraining
import com.johndev.aitrainer.common.entities.ChargeData
import com.johndev.aitrainer.common.entities.ResultsPerceptron

class RangesDatabase {

    private val operations = NeuronTraining()

    suspend fun methodRanges(w: Double, b: Double, rango1: Double, rango2: Double, dataset: DatasetComplete): MutableList<ResultsPerceptron>{
        val mayorRango: Double
        val menorRango: Double
        if (rango1 > rango2){
            mayorRango = rango1
            menorRango = rango2
        } else {
            mayorRango = rango2
            menorRango = rango1
        }
        var newW = w
        var counter = 0
        var derivadaRes: Double
        var valueJW: Double
        var error: MutableList<Double>
        var guess: MutableList<Double>
        val results = mutableListOf<ResultsPerceptron>()
        val resume = mutableListOf<ChargeData>()
        do {
            derivadaRes = operations.resolveDerivative(newW, b, dataset.valuesX, dataset.valuesY)
            guess = operations.resolveGuess(dataset.valuesX, w, b)
            error = operations.resolveError(guess, dataset.valuesY)
            valueJW = operations.resolveCost(newW, b, dataset.valuesX, dataset.valuesY)
            val charge = ChargeData(counter, newW, valueJW, valueJW)
            results.add(
                ResultsPerceptron(counter, newW, b, valuesX = dataset.valuesX, valuesY = dataset.valuesY,
                valueJW = valueJW, derivada = derivadaRes, error = error, guess = guess,
                costo = valueJW)
            )
            resume.add(charge)
            newW = operations.resolveW(newW, derivadaRes)
            counter++
        } while (mayorRango < valueJW || menorRango > valueJW)
        return results
    }

}