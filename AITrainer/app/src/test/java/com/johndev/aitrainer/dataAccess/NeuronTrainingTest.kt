package com.johndev.aitrainer.dataAccess

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`

import org.junit.Test

class NeuronTrainingTest {

    private val neuronTraining = NeuronTraining()
    private val resultGuess: MutableList<Double> = mutableListOf(33.8, 37.4, 41.0, 44.6, 48.2, 50.0, 59.0, 86.0)
    private val resultError: MutableList<Double> = mutableListOf(3.2399999999999896, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    private val valuesX: MutableList<Double> = mutableListOf(1.0, 3.0 ,5.0, 7.0, 9.0, 10.0, 15.0, 30.0)
    private val valuesY: MutableList<Double> = mutableListOf(32.0, 37.4, 41.0, 44.6, 48.2, 50.0, 59.0, 86.0)
    private val w: Double = 1.8
    private val b: Double = 32.0
    private val w0: Double = 1.8
    private val w1: Double = 32.0

    @Test
    fun resolveGuess() {
        val guess = neuronTraining.resolveGuess(valuesX, w, b)
        assertThat(resultGuess, `is`(guess))
    }

    @Test
    fun resolveError() {
        val error = neuronTraining.resolveError(resultGuess, valuesY)
        assertThat(resultError, `is`(error))
    }

    @Test
    fun resolveW() {
        val newW = neuronTraining.resolveW(w, neuronTraining.resolveDerivative(w, b, valuesX, valuesY))
        assertThat(1.799955, `is`(newW))
    }

    @Test
    fun resolveDerivative() {
        val derivative = neuronTraining.resolveDerivative(w, b, valuesX, valuesY)
        assertThat(0.22499999999999964, `is`(derivative))
    }

    @Test
    fun resolveCost() {
        val costo = neuronTraining.resolveCost(w, b, valuesX, valuesY)
        assertThat(0.20249999999999935, `is`(costo))
    }

    @Test
    fun getJ() {
        val newJ = neuronTraining.getJ(w, b, valuesX, valuesY)
        assertThat(0.20249999999999935, `is`(newJ))
    }

    @Test
    fun getAproximateW0() {
        val newW0 = neuronTraining.getAproximateW0(w0, w1, valuesX, valuesY)
        assertThat(1.745595, `is`(newW0))
    }

    @Test
    fun getAproximateW0Average() {
        val w0Average = neuronTraining.getAproximateW0Average(w0, w1, valuesX, valuesY)
        assertThat(272.025, `is`(w0Average))
    }

    @Test
    fun getAproximateW1() {
    }

    @Test
    fun getAproximateW1Average() {
    }

    @Test
    fun getMagnitude() {
    }

    @Test
    fun getResultingMagnitude() {
    }

    @Test
    fun getDifferenceMagnitude() {
    }

    @Test
    fun getSSRegresion() {
    }

    @Test
    fun getSSTotal() {
    }

    @Test
    fun getRSquared() {
    }
}