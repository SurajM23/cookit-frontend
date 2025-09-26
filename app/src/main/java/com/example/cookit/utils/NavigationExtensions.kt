package com.example.cookit.utils

import androidx.navigation.NavController

fun NavController.toUserProfile(userId: String) {
    navigate(NavigationConstants.USER_PROFILE_ROUTE.replace("{userId}", userId))
}

fun NavController.toRecipe(recipeId: String) {
    navigate(NavigationConstants.USER_RECIPE_ROUTE.replace("{recipeId}", recipeId))
}

