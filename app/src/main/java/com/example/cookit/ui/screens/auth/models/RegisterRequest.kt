package com.example.cookit.ui.screens.auth.models

data class RegisterRequest(
    val username: String,
    val name: String,
    val email: String,
    val password: String
)
