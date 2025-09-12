package com.example.cookit.data.utils


object NavigationConstants {
    const val SPLASH_SCREEN = "splash_screen"
    const val LOGIN_SCREEN = "login_screen"
    const val REGISTER_SCREEN = "register_screen"
    const val HOME_SCREEN = "home_screen"
    const val RECIPE_DETAIL_SCREEN = "recipe_detail_screen/{recipeId}"
    fun recipeDetailScreenRoute(recipeId: String) = "recipe_detail_screen/$recipeId"
}