package com.example.cookit

import RegistrationScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookit.ui.screens.auth.SplashScreen
import com.example.cookit.ui.screens.auth.screens.LoginScreen
import com.example.cookit.ui.screens.home.HomeScreen
import com.example.cookit.ui.theme.CookITTheme
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookITTheme {
                val navController = rememberNavController()
                AnimatedNavHost(
                    navController = navController,
                    startDestination = "splash",
                    enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut() },
                    popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
                    popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
                ) {
                    composable("splash") {
                        SplashScreen(
                            context = this@MainActivity,
                            onNavigateToHome = {
                                navController.navigate("home") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            },
                            onNavigateToLogin = {
                                navController.navigate("login") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("login") {
                        LoginScreen(
                            context = this@MainActivity,
                            onLoginSuccess = {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onNavigateToRegister = {
                                navController.navigate("registration")
                            }
                        )
                    }
                    composable("registration") {
                        RegistrationScreen(
                            context = this@MainActivity,
                            onRegistrationSuccess = {
                                navController.navigate("home") {
                                    popUpTo("registration") { inclusive = true }
                                }
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable("home") {
                        HomeScreen(navController)
                    }
                }
            }
        }
    }
}