package com.example.cookit.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cookit.data.network.HomeRepository
import com.example.cookit.model.AllRecipeResponse
import com.example.cookit.model.ApiResult
import com.example.cookit.model.CreateRecipeRequest
import com.example.cookit.model.Recipe
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.RecipeResponse
import com.example.cookit.model.SimpleMessageResponse
import com.example.cookit.model.UserProfile
import com.example.cookit.model.UserSuggestion
import com.example.cookit.ui.paging.RecipeFeedPagingSource
import com.example.cookit.ui.paging.getRecipeFeedPager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.cookit.data.network.toApiResult

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    val recipeFeed: Flow<PagingData<Recipe>> =
        repository.getRecipeFeedPager().cachedIn(viewModelScope)

    // ---------- User Suggestions ----------
    private val _userSuggestions =
        MutableStateFlow<ApiResult<List<UserSuggestion>>>(ApiResult.Loading)
    val userSuggestions: StateFlow<ApiResult<List<UserSuggestion>>> = _userSuggestions

    fun getUserSuggestions() {
        _userSuggestions.value = ApiResult.Loading
        viewModelScope.launch {
            try {
                val response = repository.getUserSuggestions()
                _userSuggestions.value = response.toApiResult()
            } catch (e: Exception) {
                _userSuggestions.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }


    // ---------- Follow / Unfollow ----------
    private val _followResponse =
        MutableStateFlow<ApiResult<SimpleMessageResponse>>(ApiResult.Loading)
    val followResponse: StateFlow<ApiResult<SimpleMessageResponse>> = _followResponse

    fun followUser(userId: String, isFollow: Boolean) {
        viewModelScope.launch {
            try {
                val response = if (isFollow) {
                    repository.followUser(userId)
                } else {
                    repository.unfollowUser(userId)
                }
                _followResponse.value = response.toApiResult()
            } catch (e: Exception) {
                _followResponse.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }


    // ---------- Global Feed ----------
    private val _feedState = MutableStateFlow<ApiResult<RecipeFeedResponse>>(ApiResult.Loading)
    val feedState: StateFlow<ApiResult<RecipeFeedResponse>> = _feedState

    fun getRecipeFeed(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getRecipeFeed(page)
                _feedState.value = response.toApiResult()
            } catch (e: Exception) {
                _feedState.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }

    val recipeFeedPager = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { RecipeFeedPagingSource(repository) }
    ).flow
        .cachedIn(viewModelScope)

    // ---------- User Profile ----------
    private val _profileState = MutableStateFlow<ApiResult<UserProfile>>(ApiResult.Loading)
    val profileState: StateFlow<ApiResult<UserProfile>> = _profileState

    private val _feedState2 = MutableStateFlow<ApiResult<RecipeFeedResponse>>(ApiResult.Loading)
    val feedState2: StateFlow<ApiResult<RecipeFeedResponse>> = _feedState2

    private val _recipeState = MutableStateFlow<ApiResult<RecipeResponse>>(ApiResult.Loading)
    val recipeState: StateFlow<ApiResult<RecipeResponse>> = _recipeState
    fun getUserProfile(userId: String) {
        _profileState.value = ApiResult.Loading
        viewModelScope.launch {
            try {
                val response = repository.getUserProfile(userId)
                _profileState.value = response.toApiResult()
            } catch (e: Exception) {
                _profileState.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }

    fun getUserRecipes(userId: String, page: Int = 1) {
        if (page == 1) _feedState2.value = ApiResult.Loading
        viewModelScope.launch {
            try {
                val response = repository.getUserRecipes(userId, page)
                _feedState2.value = response.toApiResult()
            } catch (e: Exception) {
                _feedState2.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }


    fun getRecipeById(recipeId: String) {
        _profileState.value = ApiResult.Loading
        viewModelScope.launch {
            try {
                _recipeState.value = ApiResult.Loading
                val response = repository.getRecipeById(recipeId)
                _recipeState.value = response.toApiResult()
            } catch (e: Exception) {
                _recipeState.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }

    private val _recipeLikedResponse =
        MutableStateFlow<ApiResult<SimpleMessageResponse>>(ApiResult.Loading)
    val recipeLikedResponse: StateFlow<ApiResult<SimpleMessageResponse>> = _recipeLikedResponse

    fun getRecipeLiked(recipeId: String) {
        _profileState.value = ApiResult.Loading
        viewModelScope.launch {
            try {
                _recipeLikedResponse.value = ApiResult.Loading
                val response = repository.getRecipeLiked(recipeId)
                _recipeLikedResponse.value = response.toApiResult()
            } catch (e: Exception) {
                _recipeLikedResponse.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }

    private val _allRecipeState = MutableStateFlow<ApiResult<AllRecipeResponse>>(ApiResult.Loading)
    val allRecipeState: StateFlow<ApiResult<AllRecipeResponse>> = _allRecipeState

    fun getAllRecipe(page: Int = 1) {
        viewModelScope.launch {
            try {
                val response = repository.getAllRecipe(page)
                _allRecipeState.value = response.toApiResult()
            } catch (e: Exception) {
                _allRecipeState.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }

    private val _createRecipeResponse =
        MutableStateFlow<ApiResult<SimpleMessageResponse>>(ApiResult.Loading)
    val createRecipeResponse: StateFlow<ApiResult<SimpleMessageResponse>> = _createRecipeResponse

    fun createRecipePost(createRecipeRequest: CreateRecipeRequest) {
        _createRecipeResponse.value = ApiResult.Loading
        viewModelScope.launch {
            try {
                _createRecipeResponse.value = ApiResult.Loading
                val response = repository.createRecipePost(createRecipeRequest)
                _createRecipeResponse.value = response.toApiResult()
            } catch (e: Exception) {
                _createRecipeResponse.value = ApiResult.Error(e.message ?: "Network error")
            }
        }
    }


}
