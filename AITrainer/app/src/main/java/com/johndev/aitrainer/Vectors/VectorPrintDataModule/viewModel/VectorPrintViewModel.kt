package com.johndev.aitrainer.Vectors.VectorPrintDataModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.aitrainer.Iterations.IterationsPrintDataModule.model.AutomaticPrintDataRepository
import com.johndev.aitrainer.Vectors.VectorPrintDataModule.model.VectorPrintDataRepository
import com.johndev.aitrainer.common.entities.ResultsAutomaticJSON
import com.johndev.aitrainer.common.entities.ResultsVector
import kotlinx.coroutines.launch

class VectorPrintViewModel : ViewModel() {

    private var repository = VectorPrintDataRepository()

    private val resultSaved = MutableLiveData<Boolean>()
    fun getResultSaved() = resultSaved

    fun createJSON(name: String, extension: String,resultList: MutableList<ResultsVector>) {
        viewModelScope.launch {
            resultSaved.value = repository.createJSON(name, extension, resultList)
        }
    }

    fun createTXT(name: String, extension: String,resultList: MutableList<ResultsVector>) {
        viewModelScope.launch {
            resultSaved.value = repository.createJSON(name, extension, resultList)
        }
    }

    fun createCSV(name: String, extension: String,resultList: MutableList<ResultsVector>) {
        viewModelScope.launch {
            resultSaved.value = repository.createJSON(name, extension, resultList)
        }
    }

}