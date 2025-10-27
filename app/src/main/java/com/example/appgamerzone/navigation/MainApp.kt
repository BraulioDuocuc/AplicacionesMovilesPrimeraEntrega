// navigation/MainApp.kt
package com.example.appgamerzone.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Función para navegar y cerrar drawer
    fun navigateWithDrawer(route: String) {
        scope.launch {
            drawerState.close()
            navController.navigate(route)
        }
    }

    // Función para abrir drawer
    val openDrawer: () -> Unit = {
        scope.launch {
            drawerState.open()
        }
    }

    // Determinar si mostrar drawer basado en la ruta actual
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val showDrawer = currentRoute == Screen.Home.route ||
            currentRoute == Screen.Catalog.route ||
            currentRoute?.startsWith(Screen.Catalog.route) == true

    if (showDrawer) {
        // Pantallas CON Navigation Drawer
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                NavigationDrawerContent(
                    onItemClick = { route ->
                        navigateWithDrawer(route)
                    },
                    onClose = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        ) {
            NavGraph(
                navController = navController,  // ← Pasar navController
                onOpenDrawer = openDrawer      // ← Pasar función para abrir drawer
            )
        }
    } else {
        // Pantallas SIN Navigation Drawer (Login, Register)
        NavGraph(
            navController = navController,      // ← Pasar navController
            onOpenDrawer = {}                   // ← No hacer nada en estas pantallas
        )
    }
}