package com.example.washinton.feature.api

import com.example.washinton.feature.products.ProductDetails
import com.example.washinton.feature.receipt.TransferOrderDetails
import com.example.washinton.feature.receipt.MessageResponse
import com.example.washinton.feature.receipt.TransferOrder
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getProductNames(): Result<List<String>> {
        return try {
            val response = apiService.getProductsNames()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProductDetails(sku: String): Result<ProductDetails> {
        return try {
            val response = apiService.getProductDetails(sku)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
        }

    suspend fun getTransferOrderDetails(id: String): Result<TransferOrderDetails> {
        return try {
            val response = apiService.getTransferOrderDetails(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Repository function: fetch list of TransferOrders and handle Response
    suspend fun getTransferOrder(): Result<List<TransferOrder>> {
        return try {
            val response = apiService.getTransferOrder()
            if (response.isSuccessful) {
                // Check if the response body is not null
                response.body()?.let { transferOrders ->
                    Result.success(transferOrders)
                } ?: Result.failure(Exception("No data available"))
            } else {
                Result.failure(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun updateTransferStatus(id: String): Result<String> {
        return try {
            val response = apiService.updateTransferStatus(id)
            if (response.isSuccessful) {
                val message = response.body()?.message ?: "Unknown error"
                Result.success(message)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateStoreStock(orderID: String): Result<String> {
        return try {
            val response = apiService.updateStoreStock(orderID)
            if (response.isSuccessful) {
                val message = response.body()?.message ?: "Unknown error"
                Result.success(message)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
