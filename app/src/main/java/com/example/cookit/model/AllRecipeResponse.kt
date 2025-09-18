package com.example.cookit.model

data class AllRecipeResponse(
    val page: Int,
    val totalPages: Int,
    val totalRecipes: Int,
    val recipes: List<Recipe>
)