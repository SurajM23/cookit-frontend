package com.example.cookit.data.network

import com.example.cookit.model.UserSuggestion
import retrofit2.Response

class HomeRepository(private val api: ApiService) {
    suspend fun getUserSuggestions(token: String): Response<List<UserSuggestion>> {
        return api.getUserSuggestions(token = token)
    }

    suspend fun followUser(token: String, userId: String): Response<Unit> {
        return api.followUser(token = token, userId = userId)
    }

}