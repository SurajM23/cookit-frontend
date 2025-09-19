package com.example.cookit.ui.screens.addPost

data class RecipeDraft(
    var title: String = "",
    var ingredients: List<String> = emptyList(),
    var steps: List<String> = emptyList(),
    var cookTime: String = "",
    var cookTimeUnit: String = "min",
    var imageUri: String? = null
)