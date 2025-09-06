package com.example.cookit.data.network

import com.example.cookit.ui.screens.home.model.UserSuggestion
import retrofit2.Response

class HomeRepository(private val api: ApiService) {
    suspend fun getUserSuggestions(token: String): Response<List<UserSuggestion>> {
        return api.getUserSuggestions(token = token)
    }
}