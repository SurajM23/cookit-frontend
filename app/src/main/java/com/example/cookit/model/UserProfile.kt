package com.example.cookit.model

data class UserProfile(
    val _id: String = "",
    val username: String = "",
    val name: String = "",
    val email: String = "",
    val avatarUrl: String = "",
    val bio: String = "",
    var followers: List<String> = emptyList(),
    val following: List<String> = emptyList(),
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = ""
)