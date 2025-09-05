package com.example.cookit.ui.screens.model

import com.example.cookit.ui.screens.auth.models.AuthResponse

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val authResponse: AuthResponse) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}