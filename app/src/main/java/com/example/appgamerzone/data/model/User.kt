package com.example.appgamerzone.data.model

// model/User.kt
data class User(
    val id: Long = 0,
    val email: String,
    val password: String,
    val fullName: String,
    val age: Int,
    val isDuocStudent: Boolean = false,
    val referralCode: String? = null,
    val levelUpPoints: Int = 0,
    val level: Int = 1,
    val createdAt: Long = System.currentTimeMillis()
)