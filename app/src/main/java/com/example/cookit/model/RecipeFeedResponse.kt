package com.example.cookit.model

data class RecipeFeedResponse(
    val page: Int,
    val totalPages: Int,
    val totalRecipes: Int,
    val recipes: List<Recipe>
)

data class Recipe(
    val _id: String,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val cookTime: Int,
    val author: Author,
    val imageUrl: String,
    val likes: List<String>,
    val likeCount: Int,
    val createdAt: String,
    val updatedAt: String
)

data class Author(
    val _id: String,
    val username: String,
    val name: String,
    val avatarUrl: String
)