package com.example.washinton.feature.batches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.washinton.feature.api.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@HiltViewModel
class RegisterBatchViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: MutableLiveData<String> = _message

    private val _batches = MutableStateFlow<Batches?>(null)
    val batches: StateFlow<Batches?> = _batches

    fun updateBatchStock(batchCode: String) {
        viewModelScope.launch {
            repository.updateBatchStatusInventory(batchCode)
                .onSuccess { message ->
                    _message.value = message
                }
                .onFailure { error ->
                    _message.value = error.message ?: "Unknown error"
                }
        }
    }

    fun getBatchDetails(batchCode: String) {
        viewModelScope.launch {
            repository.getBatchDetails(batchCode)
                .onSuccess { batches ->
                    _batches.value = batches
                }
                .onFailure { error ->
                    _message.value = error.message ?: "Unknown error"
                }
        }
    }

}