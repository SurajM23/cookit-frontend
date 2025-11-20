package com.example.cookit.utils

/**
 * Navigation route constants for the app
 * Centralized location for all navigation destinations
 */
object NavigationConstants {
    // Authentication Flow
    const val SPLASH_SCREEN = "splash_screen"
    const val LOGIN_SCREEN = "login_screen"
    const val REGISTER_SCREEN = "register_screen"
    
    // Main App Flow
    const val HOME_SCREEN = "home_screen"
    const val EXPLORE_SCREEN = "explore_screen"
    const val ADD_RECIPE_SCREEN = "add_recipe_screen"
    
    // Bottom Navigation Tabs
    const val HOME_TAB = "HOME"
    const val SEARCH_TAB = "SEARCH"
    const val FAVORITES_TAB = "FAVOURITES"
    const val PROFILE_TAB = "PROFILE"
    
    // Parameterized Routes
    const val USER_PROFILE_ROUTE = "user_profile/{userId}"
    const val USER_RECIPE_ROUTE = "user_recipe/{recipeId}"
    
    // Helper functions for parameterized routes
    fun userProfileRoute(userId: String) = "user_profile/$userId"
    fun userRecipeRoute(recipeId: String) = "user_recipe/$recipeId"
}