package com.johndev.aitrainer.Automatic.AutomaticCalculoModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.aitrainer.Automatic.AutomaticCalculoModule.model.AutomaticRepository
import com.johndev.aitrainer.common.entities.DatasetComplete
import com.johndev.aitrainer.common.entities.Automatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AutomaticViewModel : ViewModel() {

    private val repository = AutomaticRepository()

    private val resultAutomatic = MutableLiveData<MutableList<Automatic>>()
    fun getResultAutomatic() = resultAutomatic

    suspend fun automaticMethodForDiferences(umbral: Double, dataset: DatasetComplete) {
        var myResult: MutableList<Automatic> = mutableListOf()
        viewModelScope.launch (Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                myResult = repository.automaticMethodForDiferences(umbral, dataset)
            }
            resultAutomatic.value = myResult
        }
    }

}