package com.example.cookit.data.network

import com.example.cookit.model.AuthResponse
import com.example.cookit.model.LoginRequest
import com.example.cookit.model.RegisterRequest
import retrofit2.Response

class AuthRepository(private val api: ApiService) {
    suspend fun registerUser(request: RegisterRequest): Response<AuthResponse> {
        return api.registerUser(request)
    }
    suspend fun loginUser(request: LoginRequest): Response<AuthResponse> {
        return api.loginUser(request)
    }
}
