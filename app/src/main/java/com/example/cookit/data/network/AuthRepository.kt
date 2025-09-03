package com.example.cookit.data.network

import com.example.cookit.data.models.RegisterRequest
import com.example.cookit.data.models.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository(private val api: ApiService) {
    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse> {
        return api.registerUser(request)
    }
}
