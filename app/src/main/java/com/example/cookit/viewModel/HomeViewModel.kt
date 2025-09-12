package com.example.cookit.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.network.HomeRepository
import com.example.cookit.model.ApiResult
import com.example.cookit.model.UserSuggestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _userSuggestions =
        MutableStateFlow<ApiResult<List<UserSuggestion>>>(ApiResult.Loading)
    val userSuggestions: StateFlow<ApiResult<List<UserSuggestion>>> = _userSuggestions

    fun getUserSuggestions(token: String) {
        _userSuggestions.value = ApiResult.Loading
        viewModelScope.launch {
            try {
                val response = repository.getUserSuggestions(token)
                if (response.isSuccessful && response.body() != null) {
                    _userSuggestions.value = ApiResult.Success(response.body()!!)
                } else {
                    _userSuggestions.value = ApiResult.Error(response.message() ?: "Unknown error")
                }
            } catch (e: Exception) {
                _userSuggestions.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }
}