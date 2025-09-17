package com.example.cookit.model

import com.google.gson.annotations.SerializedName

data class UserSuggestion(
    val _id: String,
    val username: String,
    val name: String,
    val email: String,
    val avatarUrl: String= ""
)