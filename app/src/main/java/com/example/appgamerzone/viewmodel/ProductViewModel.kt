package com.example.appgamerzone.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgamerzone.data.local.AppDatabase
import com.example.appgamerzone.data.model.Product
import com.example.appgamerzone.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedProductsIfEmpty()
            loadCategories()
            loadProducts(null)
        }
    }

    fun loadProducts(selectedCategory: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, selectedCategory = selectedCategory)
            val products = repository.getProductsByCategory(selectedCategory)
            _uiState.value = _uiState.value.copy(products = products, loading = false)
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            val categories = repository.getCategories()
            _uiState.value = _uiState.value.copy(categories = categories)
        }
    }
}

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val loading: Boolean = true,
    val selectedCategory: String? = null
)

class ProductViewModelFactory(private val context: Context) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            val db = AppDatabase.getInstance(context)
            val repo = ProductRepository(db.productDao())
            return ProductViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}