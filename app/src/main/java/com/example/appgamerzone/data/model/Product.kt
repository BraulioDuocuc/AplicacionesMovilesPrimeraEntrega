package com.example.appgamerzone.data.model


data class Product(
    val id: Long = 0,
    val name: String,
    val price: Double,
    val category: String,
    val description: String = "",
    val imageUrl: String? = null,
    val rating: Double = 0.0,
    val reviewCount: Int = 0
)

