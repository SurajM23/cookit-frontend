package com.example.cookit.model

data class CreateRecipeRequest(
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val cookTime: Int,
    val imageUrl: String
)
