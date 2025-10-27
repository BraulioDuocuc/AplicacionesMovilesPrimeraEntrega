// navigation/MainApp.kt
package com.example.appgamerzone.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    fun navigateWithDrawer(route: String) {
        scope.launch {
            drawerState.close()
            navController.navigate(route)
        }
    }

    val openDrawer: () -> Unit = {
        scope.launch { drawerState.open() }
    }

    val backStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = backStackEntry?.destination?.route
    val showDrawer = currentRoute == Screen.Home.route ||
            currentRoute == Screen.Catalog.route ||
            currentRoute?.startsWith(Screen.Catalog.route) == true

    if (showDrawer) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                NavigationDrawerContent(
                    onItemClick = { route -> navigateWithDrawer(route) },
                    onClose = { scope.launch { drawerState.close() } }
                )
            }
        ) {
            NavGraph(
                navController = navController,
                onOpenDrawer = openDrawer
            )
        }
    } else {
        NavGraph(
            navController = navController,
            onOpenDrawer = {}
        )
    }
}