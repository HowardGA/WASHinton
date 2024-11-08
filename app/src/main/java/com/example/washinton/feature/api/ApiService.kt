package com.example.washinton.feature.api

import com.example.washinton.feature.products.ProductDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products/list-names")
    suspend fun getProductsNames(): Response<List<String>>

    @GET("products/sku/{sku}")
    suspend fun getProductDetails(@Path("sku") sku: String): ProductDetails
}

