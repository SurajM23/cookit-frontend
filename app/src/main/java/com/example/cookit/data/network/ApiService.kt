package com.example.cookit.data.network

import com.example.cookit.ui.screens.auth.models.AuthResponse
import com.example.cookit.ui.screens.auth.models.LoginRequest
import com.example.cookit.ui.screens.auth.models.RegisterRequest
import com.example.cookit.ui.screens.home.model.UserSuggestion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<AuthResponse>

    @GET("users/suggestions")
    suspend fun getUserSuggestions(
        @Header("Authorization") token: String
    ): Response<List<UserSuggestion>>

}