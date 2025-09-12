package com.example.cookit.model

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val authResponse: AuthResponse) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}