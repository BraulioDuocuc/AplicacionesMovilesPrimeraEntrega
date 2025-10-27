package com.example.appgamerzone.data.repository

import com.example.appgamerzone.data.local.dao.ProductDao
import com.example.appgamerzone.data.local.entities.ProductEntity
import com.example.appgamerzone.data.model.Product

class ProductRepository(private val productDao: ProductDao) {

    suspend fun getAllProducts(): List<Product> {
        return productDao.getAllProducts().map { it.toDomain() }
    }

    suspend fun getProductsByCategory(category: String?): List<Product> {
        return if (category.isNullOrBlank()) {
            getAllProducts()
        } else {
            productDao.getProductsByCategory(category).map { it.toDomain() }
        }
    }

    suspend fun searchProducts(query: String): List<Product> {
        return productDao.searchProducts(query).map { it.toDomain() }
    }

    suspend fun getCategories(): List<String> {
        val products = productDao.getAllProducts()
        return products.map { it.category }.distinct()
    }

    suspend fun seedProductsIfEmpty() {
        val existing = productDao.getAllProducts()
        if (existing.isNotEmpty()) return

        val seed = listOf(
            ProductEntity(
                code = "PS5-DIGITAL",
                category = "Consolas",
                name = "PlayStation 5 Digital Edition",
                price = 549990.0,
                description = "Consola Sony PS5 edición digital",
                imageUrl = null,
                rating = 4.8,
                reviewCount = 124
            ),
            ProductEntity(
                code = "XBX-S",
                category = "Consolas",
                name = "Xbox Series S",
                price = 349990.0,
                description = "Consola Microsoft Xbox Series S",
                imageUrl = null,
                rating = 4.6,
                reviewCount = 98
            ),
            ProductEntity(
                code = "ROG-STRIX-G15",
                category = "Computadores",
                name = "ASUS ROG Strix G15",
                price = 1299990.0,
                description = "Notebook gamer Ryzen + RTX",
                imageUrl = null,
                rating = 4.7,
                reviewCount = 76
            ),
            ProductEntity(
                code = "LEN-LEGION-T5",
                category = "Computadores",
                name = "Lenovo Legion T5",
                price = 1099990.0,
                description = "PC gamer con NVIDIA GeForce RTX",
                imageUrl = null,
                rating = 4.5,
                reviewCount = 54
            ),
            ProductEntity(
                code = "LOGI-G502",
                category = "Accesorios",
                name = "Logitech G502 X",
                price = 59990.0,
                description = "Mouse gamer con DPI ajustable",
                imageUrl = null,
                rating = 4.4,
                reviewCount = 210
            ),
            ProductEntity(
                code = "HYPERX-CLOUD-II",
                category = "Accesorios",
                name = "HyperX Cloud II",
                price = 79990.0,
                description = "Audífonos gamer con sonido envolvente",
                imageUrl = null,
                rating = 4.6,
                reviewCount = 320
            ),
            ProductEntity(
                code = "COUGAR-ARMOR",
                category = "Sillas",
                name = "Cougar Armor",
                price = 169990.0,
                description = "Silla gamer ergonómica",
                imageUrl = null,
                rating = 4.3,
                reviewCount = 88
            ),
            ProductEntity(
                code = "DXRACER-FORMULA",
                category = "Sillas",
                name = "DXRacer Formula",
                price = 199990.0,
                description = "Silla gamer premium",
                imageUrl = null,
                rating = 4.5,
                reviewCount = 65
            )
        )

        // Insert sequentially
        seed.forEach { productDao.insertProduct(it) }
    }
}

private fun ProductEntity.toDomain(): Product = Product(
    id = id,
    name = name,
    price = price,
    category = category,
    description = description,
    imageUrl = imageUrl,
    rating = rating,
    reviewCount = reviewCount
)