package com.example.cookit.data.network

import com.example.cookit.model.AuthResponse
import com.example.cookit.model.LoginRequest
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.RegisterRequest
import com.example.cookit.model.SimpleMessageResponse
import com.example.cookit.model.UserProfile
import com.example.cookit.model.UserSuggestion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // ---------- Auth ----------
    @POST("auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<AuthResponse>

    @POST("auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<AuthResponse>


    // ---------- Users ----------
    @GET("users/suggestions")
    suspend fun getUserSuggestions(): Response<List<UserSuggestion>>

    @GET("users/{userId}")
    suspend fun getUserProfile(
        @Path("userId") userId: String
    ): Response<UserProfile>

    @POST("users/{userId}/follow")
    suspend fun followUser(
        @Path("userId") userId: String
    ): Response<SimpleMessageResponse>

    @POST("users/{userId}/unfollow")
    suspend fun unfollowUser(
        @Path("userId") userId: String
    ): Response<SimpleMessageResponse>


    // ---------- Recipes ----------
    @GET("recipes/feed")
    suspend fun getRecipeFeed(
        @Query("page") page: Int
    ): Response<RecipeFeedResponse>

    @GET("recipes/user/{userId}")
    suspend fun getUserRecipes(
        @Path("userId") userId: String,
        @Query("page") page: Int
    ): Response<RecipeFeedResponse>


    @GET("users/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: String,
    ): Response<UserProfile>

}
