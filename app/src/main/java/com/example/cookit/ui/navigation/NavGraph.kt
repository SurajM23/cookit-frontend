package com.example.cookit.ui.navigation

import com.example.cookit.ui.screens.auth.RegistrationScreen
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
import com.example.cookit.ui.screens.addPost.AddRecipePostScreen
import com.example.cookit.ui.screens.auth.LoginScreen
import com.example.cookit.ui.screens.auth.SplashScreen
import com.example.cookit.ui.screens.home.ExploreScreen
import com.example.cookit.ui.screens.home.HomeScreen
import com.example.cookit.ui.screens.home.RecipeScreen
import com.example.cookit.ui.screens.home.UserProfileScreen
import com.example.cookit.utils.NavigationConstants
import com.example.cookit.viewModel.AuthViewModel
import com.example.cookit.viewModel.AuthViewModelFactory
import com.example.cookit.viewModel.HomeViewModel
import com.example.cookit.viewModel.HomeViewModelFactory

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

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
            HomeScreen(navController = navController, homeViewModel)
        }

        composable(NavigationConstants.USER_PROFILE_ROUTE) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            UserProfileScreen(
                navController = navController,
                userId = userId,
                viewModel = homeViewModel, {
                    navController.popBackStack()
                })
        }
        composable(NavigationConstants.USER_RECIPE_ROUTE) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
            RecipeScreen(
                navController = navController,
                recipeId = recipeId,
                viewModel = homeViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onLikeClick = {
                    homeViewModel.getRecipeLiked(recipeId)
                }
            )
        }

        composable(NavigationConstants.EXPLORE_SCREEN) { backStackEntry ->
            ExploreScreen(navController = navController, homeViewModel)
        }
        composable(NavigationConstants.ADD_RECIPE_SCREEN) { backStackEntry ->
            AddRecipePostScreen(homeViewModel, navController)
        }
    }
}

