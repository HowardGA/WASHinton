package com.example.washinton.feature.receipt

import com.example.washinton.feature.products.ProductDetails

data class TransferOrderDetails(
    val transfer_id: Int = 0,
    val store_id: Int = 0,
    val transfer_date: String = "",
    val status: String = "",
    val store: String = "",
    val details: List<TransferDetail> = emptyList()
)

data class TransferDetail(
    val transfer_detail_id: Int = 0,
    val transfer_id: Int = 0,
    val product_id: Int = 0,
    val quantity: Int = 0,
    val product: SimpleProduct = SimpleProduct()
)

data class SimpleProduct(
    val product_id: Int = 0,
    val name: String = "",
    val price: String = ""
)

data class TransferOrder(
    val transfer_id: Int = 0,
    val store_id: Int = 0,
    val transfer_date: String = "",
    val status: String = "",
    val store: String = "",
)


data class MessageResponse(val message: String)
