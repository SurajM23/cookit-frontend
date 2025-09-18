package com.example.cookit

import RegistrationScreen
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookit.data.network.AuthRepository
import com.example.cookit.data.network.HomeRepository
import com.example.cookit.data.network.RetrofitInstance
import com.example.cookit.ui.screens.auth.LoginScreen
import com.example.cookit.ui.screens.auth.SplashScreen
import com.example.cookit.ui.screens.home.HomeScreen
import com.example.cookit.ui.screens.home.RecipeScreen
import com.example.cookit.ui.screens.home.UserProfileScreen
import com.example.cookit.ui.theme.CookITTheme
import com.example.cookit.utils.NavigationConstants
import com.example.cookit.viewModel.AuthViewModel
import com.example.cookit.viewModel.AuthViewModelFactory
import com.example.cookit.viewModel.HomeViewModel
import com.example.cookit.viewModel.HomeViewModelFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookITTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // ✅ Use hiltViewModel() if you adopt Hilt instead of manual factories
    val authViewModel: AuthViewModel =
        viewModel(factory = AuthViewModelFactory(AuthRepository(RetrofitInstance.api)))
    val homeViewModel: HomeViewModel =
        viewModel(factory = HomeViewModelFactory(HomeRepository(RetrofitInstance.api)))

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
                context = context,
                onLoginSuccess = {
                    navController.navigate(NavigationConstants.HOME_SCREEN) {
                        popUpTo(NavigationConstants.SPLASH_SCREEN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(NavigationConstants.REGISTER_SCREEN) {
                        popUpTo(NavigationConstants.SPLASH_SCREEN) { inclusive = true }
                    }
                },
                viewModel = authViewModel
            )
        }

        composable(NavigationConstants.REGISTER_SCREEN) {
            RegistrationScreen(
                context = context,
                onRegistrationSuccess = {
                    navController.navigate(NavigationConstants.HOME_SCREEN) {
                        popUpTo(NavigationConstants.SPLASH_SCREEN) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() },
                viewModel = authViewModel
            )
        }

        composable(NavigationConstants.HOME_SCREEN) {
            HomeScreen(navController = navController)
        }

        composable(NavigationConstants.USER_RECIPE_ROUTE) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
            RecipeScreen(
                recipeId = recipeId,
                viewModel = homeViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
