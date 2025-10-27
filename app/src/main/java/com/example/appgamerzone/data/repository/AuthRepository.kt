package com.example.appgamerzone.data.repository

import com.example.appgamerzone.data.local.AppDatabase
import com.example.appgamerzone.data.local.dao.UserDao
import com.example.appgamerzone.data.local.entities.UserEntity
import com.example.appgamerzone.data.model.User

class AuthRepository(private val userDao: UserDao) {

    suspend fun register(user: User): Result<User> {
        val existing = userDao.getUserByEmail(user.email)
        if (existing != null) {
            return Result.failure(IllegalStateException("El correo ya está registrado"))
        }
        val entity = user.toEntity()
        val id = userDao.insertUser(entity)
        val created = entity.copy(id = id)
        return Result.success(created.toDomain())
    }

    suspend fun login(email: String, password: String): Result<User> {
        val entity = userDao.getUserByCredentials(email, password)
        return if (entity != null) {
            Result.success(entity.toDomain())
        } else {
            Result.failure(IllegalArgumentException("Credenciales inválidas"))
        }
    }
}

private fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    email = email,
    password = password,
    fullName = fullName,
    age = age,
    isDuocStudent = isDuocStudent,
    referralCode = referralCode,
    levelUpPoints = levelUpPoints,
    level = level,
    createdAt = createdAt
)

private fun UserEntity.toDomain(): User = User(
    id = id,
    email = email,
    password = password,
    fullName = fullName,
    age = age,
    isDuocStudent = isDuocStudent,
    referralCode = referralCode,
    levelUpPoints = levelUpPoints,
    level = level,
    createdAt = createdAt
)