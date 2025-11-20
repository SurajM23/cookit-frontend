package com.example.cookit.domain.repository

import com.example.cookit.model.AuthResponse
import com.example.cookit.model.LoginRequest
import com.example.cookit.model.RegisterRequest
import retrofit2.Response

interface AuthRepository {
    suspend fun registerUser(request: RegisterRequest): Response<AuthResponse>
    suspend fun loginUser(request: LoginRequest): Response<AuthResponse>
}
