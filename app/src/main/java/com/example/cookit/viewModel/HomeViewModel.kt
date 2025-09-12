package com.example.cookit.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.network.HomeRepository
import com.example.cookit.model.ApiResult
import com.example.cookit.model.RecipeFeedResponse
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

    fun followUser(token: String, userId: String) {
        viewModelScope.launch {
            try {
                val response = repository.followUser(token, userId)
                if (response.isSuccessful) {

                } else {

                }
            } catch (e: Exception) {

            }
        }
    }

    private val _feedState = MutableStateFlow<ApiResult<RecipeFeedResponse>>(ApiResult.Loading)
    val feedState: StateFlow<ApiResult<RecipeFeedResponse>> = _feedState

    fun getRecipeFeed(token: String?, page: Int) {
        _feedState.value = ApiResult.Loading
        viewModelScope.launch {
            try {
                val response = repository.getRecipeFeed(token,page)
                if (response.isSuccessful && response.body() != null) {
                    _feedState.value = ApiResult.Success(response.body()!!)
                } else {
                    _feedState.value = ApiResult.Error(response.message() ?: "Unknown error")
                }
            } catch (e: Exception) {
                _feedState.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }

}