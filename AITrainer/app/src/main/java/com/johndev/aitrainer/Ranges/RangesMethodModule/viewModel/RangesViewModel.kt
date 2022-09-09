package com.johndev.aitrainer.Ranges.RangesMethodModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.aitrainer.common.entities.DatasetComplete
import com.johndev.aitrainer.Ranges.RangesMethodModule.model.RangesRepository
import com.johndev.aitrainer.common.entities.ResultsPerceptron
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RangesViewModel : ViewModel() {

    private var repository = RangesRepository()

    private val resultOperations = MutableLiveData<MutableList<ResultsPerceptron>>()
    fun getResultOperations() = resultOperations

    fun getMethodRanges(w: Double, b: Double, rango1: Double, rango2: Double, dataset: DatasetComplete) {
        var result: MutableList<ResultsPerceptron> = mutableListOf()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                result = repository.methodRanges(w, b, rango1, rango2, dataset)
            }
            resultOperations.value = result
        }
    }

}