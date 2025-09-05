package com.example.cookit.data.network

import com.example.cookit.ui.screens.auth.models.AuthResponse
import com.example.cookit.ui.screens.auth.models.LoginRequest
import com.example.cookit.ui.screens.auth.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<AuthResponse>
}