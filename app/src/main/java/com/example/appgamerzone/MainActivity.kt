// MainActivity.kt
package com.example.appgamerzone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.appgamerzone.navigation.MainApp
import com.example.appgamerzone.navigation.NavGraph
import com.example.appgamerzone.ui.theme.AppGamerZoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppGamerZoneTheme {
                MainApp() // ← Reemplazar HomeScreen por NavGraph
            }
        }
    }
}