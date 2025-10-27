package com.example.appgamerzone.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val code: String,
    val category: String,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String?,
    val rating: Double,
    val reviewCount: Int
)