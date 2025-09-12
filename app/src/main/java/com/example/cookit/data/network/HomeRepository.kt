package com.example.cookit.data.network

import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.UserSuggestion
import retrofit2.Response

class HomeRepository(private val api: ApiService) {
    suspend fun getUserSuggestions(token: String): Response<List<UserSuggestion>> {
        return api.getUserSuggestions(token = token)
    }

    suspend fun followUser(token: String, userId: String): Response<Unit> {
        return api.followUser(token = token, userId = userId)
    }

    suspend fun getRecipeFeed(token: String?,page: Int): Response<RecipeFeedResponse> {
        return api.getRecipeFeed(token = token, page = page)
    }

    suspend fun getUserProfile(token: String?, userId: String) = api.getUserProfile(token, userId)

    suspend fun getUserRecipes(token: String?, userId: String, page: Int) =
        api.getUserRecipes(token, userId, page)
}