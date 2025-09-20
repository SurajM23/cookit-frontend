package com.example.cookit.ui.screens.addPost

import com.example.cookit.model.CreateRecipeRequest

data class RecipeDraft(
    var title: String = "My New Recipe",
    var description: String = "A delicious homemade dish",
    var ingredients: List<String> = listOf("Salt", "Water", "Oil"),
    var steps: List<String> = listOf("Step 1: Prepare ingredients", "Step 2: Cook with love"),
    var cookTime: String = "30",
    var cookTimeUnit: String = "min",
    var imageUri: String? = "https://plus.unsplash.com/premium_vector-1713201017274-e9e97d783e75?q=80&w=2128&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
)

fun RecipeDraft.toCreateRecipeRequest(imageUrl: String? = null): CreateRecipeRequest {
    return CreateRecipeRequest(
        title = title,
        description = "", // you can add description in RecipeDraft if needed
        ingredients = ingredients,
        steps = steps,
        cookTime = cookTime.toIntOrNull() ?: 0, // parse string safely
        imageUrl = imageUri
            ?: "https://plus.unsplash.com/premium_vector-1713201017274-e9e97d783e75?q=80&w=2128&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    )
}
