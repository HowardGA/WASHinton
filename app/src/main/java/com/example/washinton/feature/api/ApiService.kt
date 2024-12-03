package com.example.washinton.feature.api

import com.example.washinton.feature.batches.Batches
import com.example.washinton.feature.products.ProductDetails
import com.example.washinton.feature.products.ProductNameSku
import com.example.washinton.feature.profile.Profile
import com.example.washinton.feature.receipt.MessageResponse
import com.example.washinton.feature.receipt.TransferOrder
import com.example.washinton.feature.receipt.TransferOrderDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("products/list-names")
    suspend fun getProductsNames(): Response<List<ProductNameSku>>

    @GET("product/sku/{sku}")
    suspend fun getProductDetails(@Path("sku") sku: String): ProductDetails

    @GET("warehouse_transfer/{id}")
    suspend fun getTransferOrderDetails(@Path("id") id: String): TransferOrderDetails

    @GET("warehouse_transfer/")
    suspend fun getTransferOrder(): Response<List<TransferOrder>>

    @POST("transfer_stock_status/{id}")
    suspend fun updateTransferStatus(@Path("id") id: String): MessageResponse

    @POST("transfer_stock/{orderID}")
    suspend fun updateStoreStock(@Path("orderID") orderID: String): MessageResponse

    //this is to retrieve the information of a specific batch
    @GET("batches_details/{batchCode}")
    suspend fun getBatchDetails(@Path("batchCode") batchCode: String): Batches

    //this is to update de batch status and add the stock to the inventory at once
    @POST("batch/update-status")
    suspend fun updateBatchStatusInventory(@Body batchCodeRequest: BatchCodeRequest): Response<MessageResponse>

    @GET("fbUser/{FBID}")
    suspend fun getProfile(@Path("FBID") FBID: String): Profile

}

data class BatchCodeRequest(val code: String)
