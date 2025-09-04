package com.example.cookit.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.models.RegisterRequest
import com.example.cookit.data.network.AuthRepository
import com.example.cookit.ui.screens.model.AuthUiState
import kotlinx.coroutines.launch


class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf<AuthUiState>(AuthUiState.Idle)
        private set

    fun registerUser(request: RegisterRequest) {
        uiState = AuthUiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.registerUser(request)
                uiState = if (response.isSuccessful && response.body() != null) {
                    AuthUiState.Success(response.body()!!)
                } else {
                    AuthUiState.Error(response.errorBody()?.string() ?: "Unknown error")
                }
            } catch (e: Exception) {
                uiState = AuthUiState.Error(e.localizedMessage ?: "Error")
            }
        }
    }
}
