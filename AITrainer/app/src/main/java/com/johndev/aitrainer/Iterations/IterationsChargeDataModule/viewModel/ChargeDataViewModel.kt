package com.johndev.aitrainer.Iterations.IterationsChargeDataModule.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.aitrainer.Iterations.IterationsChargeDataModule.model.MemoryRepository
import com.johndev.aitrainer.common.entities.Dataset
import kotlinx.coroutines.launch

class ChargeDataViewModel : ViewModel() {

    private val repository = MemoryRepository()

    private val twoDimensionalResult = MutableLiveData<MutableList<Dataset>>()
    fun getTwoDimensionalResult() = twoDimensionalResult

    fun readTextFromUri(uri: Uri, context: Context) {
        viewModelScope.launch {
            twoDimensionalResult.value = repository.readTextFromUri(uri, context)
        }
    }

}