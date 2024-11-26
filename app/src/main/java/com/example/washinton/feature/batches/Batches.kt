package com.example.washinton.feature.batches

data class Batches(
    val batchID: Int,
    val code: String,
    val batchName: String,
    val status: String,
    val requestedAt: String,
    val products: List<Product>
    )

data class Product(
    val name: String,
    val quantity: Int
)