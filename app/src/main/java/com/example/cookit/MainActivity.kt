package com.example.cookit

import RegistrationScreen
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookit.data.network.AuthRepository
import com.example.cookit.data.network.RetrofitInstance
import com.example.cookit.data.utils.NavigationConstants
import com.example.cookit.ui.screens.auth.screens.LoginScreen
import com.example.cookit.ui.screens.auth.screens.SplashScreen
import com.example.cookit.ui.screens.auth.viewModel.AuthViewModel
import com.example.cookit.ui.screens.auth.viewModel.AuthViewModelFactory
import com.example.cookit.ui.screens.home.screen.HomeScreen
import com.example.cookit.ui.theme.CookITTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookITTheme {
                AppNavHost(this@MainActivity)
            }
        }
    }

    @Composable
    fun AppNavHost(context: Context) {
        val navController = rememberNavController()
        val authViewModel: AuthViewModel =
            viewModel(factory = AuthViewModelFactory(AuthRepository(RetrofitInstance.api)))

        NavHost(
            navController = navController,
            startDestination = NavigationConstants.SPLASH_SCREEN
        ) {
            composable(NavigationConstants.SPLASH_SCREEN) {
                SplashScreen(
                    onNavigateToHome = {
                        navController.navigate(NavigationConstants.HOME_SCREEN) {
                            popUpTo(NavigationConstants.SPLASH_SCREEN) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(NavigationConstants.LOGIN_SCREEN) {
                            popUpTo(NavigationConstants.SPLASH_SCREEN) { inclusive = true }
                        }
                    },
                    viewModel = authViewModel
                )
            }

            composable(NavigationConstants.LOGIN_SCREEN) {
                LoginScreen(
                    context,
                    onLoginSuccess = {
                        navController.navigate(NavigationConstants.HOME_SCREEN) {
                            popUpTo(NavigationConstants.SPLASH_SCREEN) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(NavigationConstants.REGISTER_SCREEN)
                    },
                    viewModel = authViewModel
                )
            }

            composable(NavigationConstants.REGISTER_SCREEN) {
                RegistrationScreen(
                    context,
                    onRegistrationSuccess = {
                        navController.navigate(NavigationConstants.HOME_SCREEN) {
                            popUpTo(NavigationConstants.SPLASH_SCREEN) { inclusive = true }
                        }
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    viewModel = authViewModel
                )
            }

            composable(NavigationConstants.HOME_SCREEN) {
                HomeScreen(context, navController)
            }
        }
    }
}