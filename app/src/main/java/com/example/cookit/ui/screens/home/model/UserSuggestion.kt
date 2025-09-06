package com.example.cookit.ui.screens.home.model

data class UserSuggestion(
    val _id: String,
    val username: String,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null
)