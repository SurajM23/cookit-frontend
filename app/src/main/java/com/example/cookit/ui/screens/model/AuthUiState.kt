package com.example.cookit.ui.screens.model

import com.example.cookit.data.models.RegisterResponse
import com.example.cookit.data.models.User

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val registerResponse: RegisterResponse) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}