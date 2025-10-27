package com.example.appgamerzone.view.catalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.FilterChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appgamerzone.viewmodel.ProductViewModel
import com.example.appgamerzone.viewmodel.ProductViewModelFactory
import com.example.appgamerzone.data.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    category: String? = null,
    onBackClick: () -> Unit,
    onProductClick: (Long) -> Unit
) {
    val context = LocalContext.current
    val viewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(context))
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(category) {
        // Aplica filtro inicial si viene categoría en la ruta
        viewModel.loadProducts(category)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        category?.let { "Catálogo - $it" } ?: "Catálogo de Productos"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Chips de categorías
            if (uiState.categories.isNotEmpty()) {
                Text(
                    text = "Categorías",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Chips simples: mostramos una fila vertical de chips por simplicidad
                uiState.categories.forEach { cat ->
                    FilterChip(
                        selected = uiState.selectedCategory == cat,
                        onClick = { viewModel.loadProducts(cat) },
                        label = { Text(cat) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // Chip de 'Todos'
                FilterChip(
                    selected = uiState.selectedCategory.isNullOrBlank(),
                    onClick = { viewModel.loadProducts(null) },
                    label = { Text("Todos") }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Lista de productos
            if (uiState.loading) {
                Text("Cargando productos…", style = MaterialTheme.typography.bodyMedium)
            } else {
                if (uiState.products.isEmpty()) {
                    Text("Sin productos para esta categoría", style = MaterialTheme.typography.bodyMedium)
                } else {
                    LazyColumn {
                        items(uiState.products) { product ->
                            ProductListItem(product = product, onProductClick = onProductClick)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductListItem(product: Product, onProductClick: (Long) -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(product.name, style = MaterialTheme.typography.titleMedium)
        Text("${product.category} • $${"%.0f".format(product.price)}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(product.description.ifBlank { "Sin descripción" }, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        // Futuro: clickable para detalle
        // Button(onClick = { onProductClick(product.id) }) { Text("Ver detalle") }
    }
}