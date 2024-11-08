package com.example.washinton.feature.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washinton.feature.api.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    // StateFlow for product names fetched from API
    private val _productNames = MutableStateFlow<List<String>>(emptyList())
    val productNames: StateFlow<List<String>> = _productNames.asStateFlow()

    //stateFlor for product details
    private val _productDetails = MutableStateFlow<ProductDetails?>(null)
    val productDetails: StateFlow<ProductDetails?> = _productDetails.asStateFlow()

    // StateFlow for error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // StateFlow for managing search input text
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    // StateFlow to track whether search is active
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    // Combined StateFlow for the filtered product list based on search text
    val filteredProductNames: StateFlow<List<String>> = _searchText
        .combine(_productNames) { text, products ->
            if (text.isBlank()) {
                products
            } else {
                products.filter { product ->
                    product.contains(text, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    // Function to update search text
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    // Toggle search mode on or off
    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            _searchText.value = ""
        }
    }

    // Fetch product names from repository
    fun fetchProductNames() {
        viewModelScope.launch {
            repository.getProductNames()
                .onSuccess { names ->
                    _productNames.value = names
                }
                .onFailure { error ->
                    _errorMessage.value = error.message
                }
        }
    }

    fun fetchProductDetails(sku: String) {
        viewModelScope.launch {
            repository.getProductDetails(sku)
                .onSuccess { details ->
                    _productDetails.value = details
                    Log.d("SearchProductsScreen", "Fetched Product Details: $details")
                }
                .onFailure { error ->
                    _errorMessage.value = error.message
                    Log.d("SearchProductsScreen", "Error fetching product details: ${error.message}")
                }
        }
    }

}
