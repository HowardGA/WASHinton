package com.example.washinton.feature.receipt

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.washinton.feature.api.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TransferOrderDetailsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    // StateFlow for the order details fetched from the API
    private val _orderDetails = MutableStateFlow<TransferOrderDetails?>(null)
    val orderDetails: StateFlow<TransferOrderDetails?> = _orderDetails.asStateFlow()

    // stateflow of order without details
    private val _order = MutableStateFlow<List<TransferOrder>>(emptyList())
    val order: StateFlow<List<TransferOrder>> = _order

    // StateFlow for error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    suspend fun getTransferOrderDetails(id: String): Result<TransferOrderDetails> {
        return repository.getTransferOrderDetails(id)
            .onSuccess { details ->
                _orderDetails.value = details
            }
            .onFailure { error ->
                _errorMessage.value = error.message
            }
    }

    suspend fun getTransferOrder() {
        repository.getTransferOrder()
            .onSuccess { details ->
                _order.value = details // Update with the list of TransferOrder
            }
            .onFailure { error ->
                _errorMessage.value = error.message
            }
    }

    suspend fun updateTransferStatus(id: String) {
        repository.updateTransferStatus(id)
            .onSuccess { message ->
                _errorMessage.value = message
            }
            .onFailure { error ->
                _errorMessage.value = error.message

            }
    }

    suspend fun updateStoreStock(orderID: String) {
        repository.updateStoreStock(orderID)
            .onSuccess { message ->
                _errorMessage.value = message
            }
            .onFailure { error ->
                _errorMessage.value = error.message

            }
    }

}