package com.johndev.aitrainer.Iterations.IterationsPrintDataModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.aitrainer.common.entities.ResultsJSON
import com.johndev.aitrainer.Iterations.IterationsPrintDataModule.model.PrintDataRepository
import kotlinx.coroutines.launch

class PrintViewModel : ViewModel() {

    private var repository = PrintDataRepository()

    private val resultSaved = MutableLiveData<Boolean>()
    fun getResultSaved() = resultSaved

    fun createJSON(name: String, extension: String,resultList: MutableList<ResultsJSON>) {
        viewModelScope.launch {
            resultSaved.value = repository.createJSON(name, extension, resultList)
        }
    }

    fun createTXT(name: String, extension: String,resultList: MutableList<ResultsJSON>) {
        viewModelScope.launch {
            resultSaved.value = repository.createJSON(name, extension, resultList)
        }
    }

    fun createCSV(name: String, extension: String,resultList: MutableList<ResultsJSON>) {
        viewModelScope.launch {
            resultSaved.value = repository.createJSON(name, extension, resultList)
        }
    }

}