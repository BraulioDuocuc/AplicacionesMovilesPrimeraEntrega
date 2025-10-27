package com.example.appgamerzone.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.HorizontalDivider

// navigation/NavigationDrawerContent.kt - Contenido mejorado
// navigation/NavigationDrawerContent.kt - Rutas corregidas
@Composable
fun NavigationDrawerContent(
    onItemClick: (String) -> Unit,
    onClose: () -> Unit
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            // Header del drawer
            Text(
                "Level-Up Gamer",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 12.dp)
            )

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            // Sección Principal
            Text(
                "MENÚ PRINCIPAL",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            NavigationDrawerItem(
                label = { Text("Mi Perfil") },
                icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                selected = false,
                onClick = { onItemClick(Screen.Profile.route) }, // ← Ruta corregida
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )

            NavigationDrawerItem(
                label = { Text("Catálogo Completo") },
                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Catálogo") },
                selected = false,
                onClick = { onItemClick(Screen.Catalog.route) }, // ← Ruta corregida
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sección Administración
            Text(
                "ADMINISTRACIÓN",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            NavigationDrawerItem(
                label = { Text("Gestión de Productos") },
                icon = { Icon(Icons.Default.Edit, contentDescription = "Productos") },
                selected = false,
                onClick = { onItemClick(Screen.ProductManagement.route) }, // ← Ruta corregida
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )

            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider()

            NavigationDrawerItem(
                label = { Text("Cerrar Sesión") },
                icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar Sesión") },
                selected = false,
                onClick = { onItemClick(Screen.Login.route) }, // ← Ruta corregida
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}