package com.example.cookit.data.repository

import com.example.cookit.data.network.ApiService
import com.example.cookit.domain.repository.AuthRepository
import com.example.cookit.model.AuthResponse
import com.example.cookit.model.LoginRequest
import com.example.cookit.model.RegisterRequest
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun registerUser(request: RegisterRequest): Response<AuthResponse> {
        return apiService.registerUser(request)
    }

    override suspend fun loginUser(request: LoginRequest): Response<AuthResponse> {
        return apiService.loginUser(request)
    }
}
