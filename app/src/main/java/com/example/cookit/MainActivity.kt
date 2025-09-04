package com.example.cookit

import RegistrationScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookit.ui.screens.home.HomeScreen
import com.example.cookit.ui.theme.CookITTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookITTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "registration") {
                    composable("registration") {
                        RegistrationScreen(
                            this@MainActivity,
                            onRegistrationSuccess = {
                                navController.navigate("home") {
                                    popUpTo("registration") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("home") {
                        HomeScreen()
                    }
                }
            }
        }
    }
}