package com.example.cookit.data.network

import com.example.cookit.model.AllRecipeResponse
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.RecipeResponse
import com.example.cookit.model.SimpleMessageResponse
import com.example.cookit.model.UserProfile
import com.example.cookit.model.UserSuggestion
import retrofit2.Response

class HomeRepository(private val api: ApiService) {

    suspend fun getUserSuggestions(): Response<List<UserSuggestion>> {
        return api.getUserSuggestions()
    }

    suspend fun getRecipeFeed(page: Int): Response<RecipeFeedResponse> {
        return api.getRecipeFeed(page)
    }

    suspend fun getUserProfile(userId: String): Response<UserProfile> {
        return api.getUserProfile(userId)
    }

    suspend fun getUserRecipes(
        userId: String,
        page: Int
    ): Response<RecipeFeedResponse> {
        return api.getUserRecipes(userId, page)
    }

    suspend fun getUserById(userId: String): Response<UserProfile> {
        return api.getUserById(userId)
    }

    suspend fun followUser(userId: String): Response<SimpleMessageResponse> {
        return api.followUser(userId)
    }

    suspend fun unfollowUser(userId: String): Response<SimpleMessageResponse> {
        return api.unfollowUser(userId)
    }

    suspend fun getRecipeById(recipeId: String): Response<RecipeResponse> {
        return api.getRecipeById(recipeId)
    }

    suspend fun getRecipeLiked(recipeId: String): Response<SimpleMessageResponse> {
        return api.getRecipeLiked(recipeId)
    }

    suspend fun getAllRecipe(page: Int): Response<AllRecipeResponse> {
        return api.getAllRecipe(page)
    }


}
