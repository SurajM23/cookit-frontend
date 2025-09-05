package com.example.cookit.data.network

import com.example.cookit.ui.screens.auth.models.AuthResponse
import com.example.cookit.ui.screens.auth.models.LoginRequest
import com.example.cookit.ui.screens.auth.models.RegisterRequest
import retrofit2.Response

class AuthRepository(private val api: ApiService) {
    suspend fun registerUser(request: RegisterRequest): Response<AuthResponse> {
        return api.registerUser(request)
    }
    suspend fun loginUser(request: LoginRequest): Response<AuthResponse> {
        return api.loginUser(request)
    }
}
