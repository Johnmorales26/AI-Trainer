package com.johndev.aitrainer.Iterations.IterationsChargeDataModule.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.aitrainer.Iterations.IterationsChargeDataModule.model.RangesMemoryRepository
import com.johndev.aitrainer.common.entities.Dataset
import kotlinx.coroutines.launch

class RangesChargeDataViewModel : ViewModel() {

    private val repository = RangesMemoryRepository()

    private val twoDimensionalResult = MutableLiveData<MutableList<Dataset>>()
    fun getTwoDimensionalResult() = twoDimensionalResult

    fun readTextFromUri(uri: Uri, context: Context) {
        viewModelScope.launch {
            twoDimensionalResult.value = repository.readTextFromUri(uri, context)
        }
    }

}