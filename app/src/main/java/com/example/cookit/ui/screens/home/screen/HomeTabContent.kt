package com.example.cookit.ui.screens.home.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookit.data.network.HomeRepository
import com.example.cookit.data.network.RetrofitInstance
import com.example.cookit.data.utils.PrefManager
import com.example.cookit.ui.screens.home.composables.HomeTabContentGrid
import com.example.cookit.ui.screens.home.model.ApiResult
import com.example.cookit.ui.screens.home.model.UserSuggestion
import com.example.cookit.ui.screens.home.viewModel.HomeViewModel
import com.example.cookit.ui.screens.home.viewModel.HomeViewModelFactory
import com.example.cookit.ui.theme.PrimaryColor

@Composable
fun HomeTabContent(context: Context) {
    val repository = HomeRepository(RetrofitInstance.api)
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(repository))

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

                if (list.isEmpty()) {
                    Text(
                        text = "No users found.",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray
                    )
                } else {
                    HomeTabContentGrid(list)
                }
            }

            is ApiResult.Error -> {
                val error = (suggestionsState as ApiResult.Error).message
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Failed to load users.",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = error,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.getUserSuggestions(PrefManager.getInstance(context).getToken() ?: "")
    }
}
