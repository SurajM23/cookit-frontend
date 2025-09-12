package com.example.cookit.ui.screens.home

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cookit.utils.PrefManager
import com.example.cookit.ui.composables.ErrorMessageBox
import com.example.cookit.ui.composables.UserSuggestionGrid
import com.example.cookit.model.ApiResult
import com.example.cookit.model.UserSuggestion
import com.example.cookit.viewModel.HomeViewModel
import com.example.cookit.ui.theme.PrimaryColor

@Composable
fun HomeTabContent(
    context: Context,
    homeViewModel: HomeViewModel
) {
    val suggestionsState by homeViewModel.userSuggestions.collectAsState()

    Box(Modifier.fillMaxSize()) {
        when (suggestionsState) {
            is ApiResult.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = PrimaryColor
                )
            }

            is ApiResult.Success -> {
                val list = (suggestionsState as ApiResult.Success<List<UserSuggestion>>).data
                UserSuggestionGrid(
                    userList = list,
                    modifier = Modifier.fillMaxSize(),
                    emptyMessage = "No users found."
                )
            }

            is ApiResult.Error -> {
                val error = (suggestionsState as ApiResult.Error).message
                ErrorMessageBox(
                    errorTitle = "Failed to load users.",
                    errorMessage = error
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        if (suggestionsState !is ApiResult.Success) {
            homeViewModel.getUserSuggestions(PrefManager.getInstance(context).getToken() ?: "")
        }
    }
}

