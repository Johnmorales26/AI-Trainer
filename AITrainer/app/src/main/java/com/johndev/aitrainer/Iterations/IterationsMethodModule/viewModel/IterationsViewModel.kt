package com.johndev.aitrainer.Iterations.IterationsMethodModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.aitrainer.common.entities.DatasetComplete
import com.johndev.aitrainer.Iterations.IterationsMethodModule.model.IterationsRepository
import com.johndev.aitrainer.common.entities.ResultsPerceptron
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IterationsViewModel : ViewModel() {

    private val repository = IterationsRepository()

    private val resultOperations = MutableLiveData<MutableList<ResultsPerceptron>>()
    fun getResultOperations() = resultOperations

    fun getMethodIterator(valueB: Double, valueW: Double, iterations: Double, dataset: DatasetComplete) {
        var result: MutableList<ResultsPerceptron> = mutableListOf()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                result = repository.methodIterator(valueB, valueW, iterations, dataset)
            }
            resultOperations.value = result
        }
    }

}