package com.example.cookit.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.models.RegisterRequest
import com.example.cookit.data.network.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var registrationMessage by mutableStateOf("")
        private set

    fun registerUser(request: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response = repository.registerUser(request)
                registrationMessage = if (response.isSuccessful && response.body() != null) {
                    "Registration successful! Welcome ${response.body()!!.name}"
                } else {
                    "Registration failed: ${response.errorBody()?.string() ?: "Unknown error"}"
                }
            } catch (e: Exception) {
                registrationMessage = "Error: ${e.localizedMessage}"
            }
        }
    }
}
