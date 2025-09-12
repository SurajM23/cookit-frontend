package com.example.cookit.model

data class RegisterRequest(
    val username: String,
    val name: String,
    val email: String,
    val password: String
)
