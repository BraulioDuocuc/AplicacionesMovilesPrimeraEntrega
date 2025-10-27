package com.example.appgamerzone.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String,
    val password: String,
    val fullName: String,
    val age: Int,
    val isDuocStudent: Boolean,
    val referralCode: String?,
    val levelUpPoints: Int,
    val level: Int,
    val createdAt: Long
)