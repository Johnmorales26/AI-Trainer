package NeuronTraining

import java.text.Format
import kotlin.math.pow
import kotlin.math.sqrt

class NeuronTraining {

    private var alpha = 0.0002

    fun resolveGuess(valuesX: MutableList<Double>, w: Double, b: Double): MutableList<Double>{
        val size = valuesX.size
        var i = 0
        val guess: MutableList<Double> = mutableListOf()
        while (i < size){
            val result = (w * valuesX[i]) + b
            guess.add(result)
            i++
        }
        return guess
    }

    fun resolveError(guess: MutableList<Double>, valuesY: MutableList<Double>): MutableList<Double>{
        val size = valuesY.size
        var i = 0
        val errorC: MutableList<Double> = mutableListOf()
        while (i < size){
            val result = (valuesY[i] - guess[i]).pow(2)
            errorC.add(result)
            i++
        }
        return errorC
    }

    fun resolveW(w: Double, result: Double): Double = (w - (alpha * result)).toDouble()

    fun resolveDerivative(w: Double, b: Double, valuesX: MutableList<Double>, valuesY: MutableList<Double>): Double{
        var countResult = 0.0
        var counter = 0
        while (counter < valuesX.size){
            countResult += (valuesX[counter] * (valuesX[counter] * w + b - valuesY[counter]))
            counter++
        }
        val size = valuesX.size.toString()
        val numerator = 1 / size.toDouble()
        return (numerator * countResult)
    }

    fun resolveCost(w: Double, b: Double, valuesX: MutableList<Double>, valuesY: MutableList<Double>): Double{
        var countResult = 0.0
        var counter = 0
        while (counter < valuesX.size){
            countResult += (valuesY[counter] - ((w * valuesX[counter]) + b)).pow(2)
            counter++
        }
        val size = valuesX.size.toString()
        val numerator = 1 / (size.toDouble() * 2)
        return (numerator * countResult)
    }

    fun getJ(w: Double,b: Double, valuesX: MutableList<Double>, valuesY: MutableList<Double>): Double{
        var countResult = 0.0
        var counter = 0
        while (counter < valuesX.size){
            countResult += (valuesY[counter] - ((w * valuesX[counter]) + b)).pow(2)
            counter++
        }
        val size = valuesX.size.toString()
        val numerator = 1 / (size.toDouble() * 2)
        return (numerator * countResult)
    }

    fun getAproximateW0(W0: Double, W1: Double, X: MutableList<Double>, Y: MutableList<Double>): Double {
        var i = 0
        var sumatoria = 0.0
        while (i < X.size) {
            sumatoria += (W0 + (W1 * X[i]) - Y[i])
            i++
        }
        val size = X.size.toString()
        val numerator = 1 / size.toDouble()
        return (W0 - alpha * (numerator * sumatoria)).toDouble()
    }

    fun getAproximateW0Average(W0: Double, W1: Double, X: MutableList<Double>, Y: MutableList<Double>): Double {
        var i = 0
        var sumatoria = 0.0
        while (i < X.size) {
            sumatoria += (W0 + (W1 * X[i]) - Y[i])
            i++
        }
        val size = X.size.toString().trim()
        val numerator = (1 / size.toDouble())
        return (numerator * sumatoria)
    }

    fun getAproximateW1(W0: Double, W1: Double, X: MutableList<Double>, Y: MutableList<Double>): Double {
        var i = 0
        var sumatoria = 0.0
        while (i < X.size) {
            sumatoria += X[i] * ((W1 * X[i]) + W0 - Y[i])
            i++
        }
        val size = X.size.toString()
        val numerator = 1 / size.toDouble()
        return (W1 - alpha * (numerator * sumatoria)).toDouble()
    }

    fun getAproximateW1Average(W0: Double, W1: Double, X: MutableList<Double>, Y: MutableList<Double>): Double {
        var i = 0
        var sumatoria = 0.0
        while (i < X.size) {
            sumatoria += X[i] * ((W1 * X[i]) + W0 - Y[i])
            i++
        }
        val size = X.size.toString()
        val numerator = (1 / size.toDouble())
        return (numerator * sumatoria)
    }

    fun getMagnitude(Ax: Double, Ay: Double): Double {
        return sqrt(Ax.pow(2) + Ay.pow(2))
    }

    fun getResultingMagnitude(J: Double, W1: Double): Double {
        return sqrt(J.toDouble() + W1.toDouble()).toDouble()
    }

    fun getDifferenceMagnitude(W0: Double, W1: Double): Double = W0 - W1

    fun getSSRegresion(y_i: MutableList<Double>, y_r: MutableList<Double>): Double {
        var ssRegresion = 0.0
        var i = 0
        while (i < y_i.size) {
            ssRegresion += (y_i[i] - y_r[i]).pow(2)
            i++
        }
        return ssRegresion
    }

    fun getSSTotal(y_i: MutableList<Double>): Double {
        var averageY = 0.0
        y_i.forEach { averageY += it }
        averageY /= y_i.size
        var ssTotal = 0.0
        y_i.forEach { ssTotal += (it - averageY).pow(2) }
        return ssTotal
    }

    fun getRSquared(ssRegresion: Double, ssTotal: Double) = 1 - (ssRegresion / ssTotal)
    
}