package com.example.washinton.feature.api

import com.example.washinton.feature.products.ProductDetails
import com.example.washinton.feature.receipt.MessageResponse
import com.example.washinton.feature.receipt.TransferOrder
import com.example.washinton.feature.receipt.TransferOrderDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("products/list-names")
    suspend fun getProductsNames(): Response<List<String>>

    @GET("product/sku/{sku}")
    suspend fun getProductDetails(@Path("sku") sku: String): ProductDetails

    @GET("warehouse_transfer/{id}")
    suspend fun getTransferOrderDetails(@Path("id") id: String): TransferOrderDetails

    @GET("warehouse_transfer/")
    suspend fun getTransferOrder(): Response<List<TransferOrder>>

    @POST("transfer_stock_status/{id}")
    suspend fun updateTransferStatus(@Path("id") id: String): Response<MessageResponse>

    @POST("transfer_stock/{orderID}")
    suspend fun updateStoreStock(@Path("orderID") orderID: String): Response<MessageResponse>
}

