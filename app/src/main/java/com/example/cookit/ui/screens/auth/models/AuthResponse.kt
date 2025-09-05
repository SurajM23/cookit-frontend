package com.example.cookit.ui.screens.auth.models


data class AuthResponse(
    val message: String,
    val token: String,
    val user: User
)

data class User(
    val id: String,
    val username: String,
    val name: String,
    val email: String
)