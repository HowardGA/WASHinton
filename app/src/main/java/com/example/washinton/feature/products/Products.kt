package com.example.washinton.feature.products

import androidx.core.util.Supplier


data class Products(
    val category: String = "",
    val description: String = "",
    val id: Int = 0,
    val image: String = "",
    val price: Double = 0.0,
    val name: String = "",
    val supplier: String = "",
    val unit: String = "",
    val weight: Double = 0.0
)