package com.example.washinton.feature.api

import com.example.washinton.feature.products.ProductDetails
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
    }
