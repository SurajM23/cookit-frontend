package com.example.cookit.data.repository

import com.example.cookit.data.network.ApiService
import com.example.cookit.domain.repository.HomeRepository
import com.example.cookit.model.AllRecipeResponse
import com.example.cookit.model.CreateRecipeRequest
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.RecipeResponse
import com.example.cookit.model.SimpleMessageResponse
import com.example.cookit.model.UserProfile
import com.example.cookit.model.UserSuggestion
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HomeRepository {

    override suspend fun getUserSuggestions(): Response<List<UserSuggestion>> {
        return apiService.getUserSuggestions()
    }

    override suspend fun getRecipeFeed(page: Int): Response<RecipeFeedResponse> {
        return apiService.getRecipeFeed(page)
    }

    override suspend fun getUserProfile(userId: String): Response<UserProfile> {
        return apiService.getUserProfile(userId)
    }

    override suspend fun getUserRecipes(
        userId: String,
        page: Int
    ): Response<RecipeFeedResponse> {
        return apiService.getUserRecipes(userId, page)
    }

    override suspend fun getUserById(userId: String): Response<UserProfile> {
        return apiService.getUserById(userId)
    }

    override suspend fun followUser(userId: String): Response<SimpleMessageResponse> {
        return apiService.followUser(userId)
    }

    override suspend fun unfollowUser(userId: String): Response<SimpleMessageResponse> {
        return apiService.unfollowUser(userId)
    }

    override suspend fun getRecipeById(recipeId: String): Response<RecipeResponse> {
        return apiService.getRecipeById(recipeId)
    }

    override suspend fun getRecipeLiked(recipeId: String): Response<SimpleMessageResponse> {
        return apiService.getRecipeLiked(recipeId)
    }

    override suspend fun getAllRecipe(page: Int): Response<AllRecipeResponse> {
        return apiService.getAllRecipe(page)
    }

    override suspend fun getUserLikedRecipe(userId: String): Response<AllRecipeResponse> {
        return apiService.getUserLikedRecipe(userId)
    }

    override suspend fun createRecipePost(createRecipeRequest: CreateRecipeRequest): Response<SimpleMessageResponse> {
        return apiService.createRecipePost(createRecipeRequest)
    }
}
