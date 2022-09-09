package com.johndev.aitrainer.Vectors.VectorChargeDataModule.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.aitrainer.Vectors.VectorChargeDataModule.model.VectorChargeDataRepository
import com.johndev.aitrainer.common.entities.ChargeVectorData
import kotlinx.coroutines.launch

class VectorChargeDataViewModel : ViewModel() {

    private val repository = VectorChargeDataRepository()

    private val datasetResult = MutableLiveData<MutableList<MutableList<Double>>>()
    private val sortedDatasetResult = MutableLiveData<ChargeVectorData>()
    fun getDatasetResult() = datasetResult
    fun getSortedDatasetResult() = sortedDatasetResult

    fun readTextFromUri(uri: Uri, context: Context) {
        viewModelScope.launch {
            datasetResult.value = repository.readTextFromUri(uri, context)
        }
    }
    fun sortArrays(dataset: MutableList<MutableList<Double>>) {
        viewModelScope.launch {
            sortedDatasetResult.value = repository.sortArrays(dataset)
        }
    }

}