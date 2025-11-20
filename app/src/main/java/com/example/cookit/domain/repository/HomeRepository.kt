package com.example.cookit.domain.repository

import com.example.cookit.model.AllRecipeResponse
import com.example.cookit.model.CreateRecipeRequest
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.RecipeResponse
import com.example.cookit.model.SimpleMessageResponse
import com.example.cookit.model.UserProfile
import com.example.cookit.model.UserSuggestion
import retrofit2.Response

interface HomeRepository {
    suspend fun getUserSuggestions(): Response<List<UserSuggestion>>
    suspend fun getRecipeFeed(page: Int): Response<RecipeFeedResponse>
    suspend fun getUserProfile(userId: String): Response<UserProfile>
    suspend fun getUserRecipes(userId: String, page: Int): Response<RecipeFeedResponse>
    suspend fun getUserById(userId: String): Response<UserProfile>
    suspend fun followUser(userId: String): Response<SimpleMessageResponse>
    suspend fun unfollowUser(userId: String): Response<SimpleMessageResponse>
    suspend fun getRecipeById(recipeId: String): Response<RecipeResponse>
    suspend fun getRecipeLiked(recipeId: String): Response<SimpleMessageResponse>
    suspend fun getAllRecipe(page: Int): Response<AllRecipeResponse>
    suspend fun getUserLikedRecipe(userId: String): Response<AllRecipeResponse>
    suspend fun createRecipePost(createRecipeRequest: CreateRecipeRequest): Response<SimpleMessageResponse>
}
