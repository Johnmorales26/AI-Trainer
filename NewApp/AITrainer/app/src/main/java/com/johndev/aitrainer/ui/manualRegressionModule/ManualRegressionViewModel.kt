package com.johndev.aitrainer.ui.manualRegressionModule

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.aitrainer.ui.manualRegressionModule.manualModel.ManualRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManualRegressionViewModel @Inject constructor(
    private val repository: ManualRepository
) : ViewModel() {

    private val _dataset = MutableLiveData<List<List<Double>>>()
    val dataset: LiveData<List<List<Double>>> = _dataset

    fun readTextFromUri(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _dataset.postValue(repository.readTextFromUri(uri))
        }
    }

}