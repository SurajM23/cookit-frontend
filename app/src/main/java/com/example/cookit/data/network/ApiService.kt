package com.example.cookit.data.network

import com.example.cookit.model.AuthResponse
import com.example.cookit.model.LoginRequest
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.RegisterRequest
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
    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<AuthResponse>

    @GET("users/suggestions")
    suspend fun getUserSuggestions(
        @Header("Authorization") token: String
    ): Response<List<UserSuggestion>>

    @POST("users/{userId}/follow")
    suspend fun followUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Response<Unit>

    @GET("recipes/feed")
    suspend fun getRecipeFeed(
        @Header("Authorization") token: String?,
        @Query("page") page: Int
    ): Response<RecipeFeedResponse>

    @GET("users/{userId}")
    suspend fun getUserProfile(
        @Header("Authorization") token: String?,
        @Path("userId") userId: String
    ): Response<UserProfile>

    @GET("recipes/user/{userId}")
    suspend fun getUserRecipes(
        @Header("Authorization") token: String?,
        @Path("userId") userId: String,
        @Query("page") page: Int
    ): Response<RecipeFeedResponse>
}