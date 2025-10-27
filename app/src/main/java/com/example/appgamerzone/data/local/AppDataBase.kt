package com.example.appgamerzone.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appgamerzone.data.local.dao.ProductDao
import com.example.appgamerzone.data.local.dao.UserDao
import com.example.appgamerzone.data.local.entities.ProductEntity
import com.example.appgamerzone.data.local.entities.UserEntity

// data/local/AppDatabase.kt
@Database(
    entities = [UserEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelup_gamer_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}