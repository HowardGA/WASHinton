package com.example.washinton.feature.products

data class ProductDetails(
    val product_id: Int,
    val name: String,
    val sku: String,
    val description: String,
    val price: String,
    val status: String,
    val image: String?,
    val category_id: Int,
    val supplier_id: Int,
    val type: String,
    val created_at: String?,
    val updated_at: String?
)
