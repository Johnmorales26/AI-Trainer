package com.johndev.aitrainer.Models

data class ResultsPerceptron (val id: Int,
                              val valueW: Double,
                              val valorB: Double,
                              val iterations: Int? = null,
                              val maxVal: Double? = null,
                              val minVal: Double? = null,
                              val valuesX: MutableList<Double>,
                              val valuesY: MutableList<Double>,
                              val valueJW: Double,
                              val derivada: Double,
                              val error: MutableList<Double>,
                              val guess: MutableList<Double>,
val costo: Double)