// navigation/NavGraph.kt
package com.example.appgamerzone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.appgamerzone.view.auth.LoginScreen
import com.appgamerzone.view.auth.RegisterScreen
import com.appgamerzone.view.home.HomeScreen
import com.example.appgamerzone.view.catalog.CatalogScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    onOpenDrawer: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Register.route
    ) {
        // ==================== PANTALLAS SIN DRAWER ====================
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // ==================== PANTALLAS CON DRAWER ====================
        composable(route = Screen.Home.route) {
            HomeScreen(
                onOpenDrawer = onOpenDrawer
            )
        }

        composable(route = Screen.Catalog.route) {
            CatalogScreen(
                category = null,
                onBackClick = { navController.popBackStack() },
                onProductClick = { productId ->
                    // TODO: Navegar a detalle de producto
                }
            )
        }

        composable(route = Screen.Catalog.route + "/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            CatalogScreen(
                category = category,
                onBackClick = { navController.popBackStack() },
                onProductClick = { productId ->
                    // TODO: Navegar a detalle de producto
                }
            )
        }

        // ==================== NUEVAS PANTALLAS DEL DRAWER ====================
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = "product_management") {
            ProductManagementScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

// Pantalla temporal de Perfil
@Composable
fun ProfileScreen(onBackClick: () -> Unit) {
    CatalogScreen(
        category = "Mi Perfil - En desarrollo",
        onBackClick = onBackClick,
        onProductClick = {}
    )
}

// Pantalla temporal de Gestión de Productos
@Composable
fun ProductManagementScreen(onBackClick: () -> Unit) {
    CatalogScreen(
        category = "Gestión de Productos - En desarrollo",
        onBackClick = onBackClick,
        onProductClick = {}
    )
}