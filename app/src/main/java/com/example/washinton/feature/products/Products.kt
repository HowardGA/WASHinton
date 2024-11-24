package com.example.washinton.feature.products

data class ProductDetails(
    val product_id: Int,
    val name: String,
    val sku: String,
    val brand: String,
    val description: String,
    val price: String,
    val status: String,
    val category: String, //has to give me the name
    val supplier: String, //has to give me the supplier name
    val type: String,
    val volume: String,
    val unit: String,
    val image: String
    )
