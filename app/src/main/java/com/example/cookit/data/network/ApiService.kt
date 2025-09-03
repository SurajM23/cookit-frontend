package com.example.cookit.data.network

import com.example.cookit.data.models.RegisterRequest
import com.example.cookit.data.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>
}