package com.johndev.aitrainer.Vectors.VectorOperationsModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.aitrainer.Vectors.VectorOperationsModule.model.VectorRepository
import com.johndev.aitrainer.common.entities.VectorResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VectorOperationsViewModel : ViewModel() {

    private val repository = VectorRepository()

    private val resultOperations = MutableLiveData<VectorResults>()
    fun getResultOperations() = resultOperations

    fun vectorCalculatios(umbral: Double, arrayXz: Array<DoubleArray>, arrayYz: Array<DoubleArray>, arrayWz: Array<DoubleArray>) {
        var result: VectorResults
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                result = repository.vectorCalculatios(umbral, arrayXz, arrayYz, arrayWz)
            }
            resultOperations.value = result
        }
    }

}