package com.example.cookit.data.models

data class RegisterRequest(
    val username: String,
    val name: String,
    val email: String,
    val password: String
)
