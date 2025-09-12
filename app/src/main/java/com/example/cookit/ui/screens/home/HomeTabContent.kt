package com.example.cookit.ui.screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cookit.utils.PrefManager
import com.example.cookit.model.ApiResult
import com.example.cookit.model.UserSuggestion
import com.example.cookit.ui.composables.RecipeCard
import com.example.cookit.ui.composables.UserSuggestionCard
import com.example.cookit.viewModel.HomeViewModel
import com.example.cookit.ui.theme.PrimaryColor

@Composable
fun HomeTabContent(
    context: Context,
    homeViewModel: HomeViewModel
) {
    val suggestionsState by homeViewModel.userSuggestions.collectAsState()
    val feedState by homeViewModel.feedState.collectAsState()

    // Fetch data only if not present
    LaunchedEffect(Unit) {
        if (suggestionsState !is ApiResult.Success) {
//            homeViewModel.getUserSuggestions(PrefManager.getInstance(context).getToken() ?: "")
        }
        if (feedState !is ApiResult.Success) {
            homeViewModel.getRecipeFeed(PrefManager.getInstance(context).getToken(), page = 1)
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

    /*    when (suggestionsState) {
            is ApiResult.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
//                    CircularProgressIndicator(color = PrimaryColor)
                }
            }

            is ApiResult.Success -> {
                val userList = (suggestionsState as ApiResult.Success<List<UserSuggestion>>).data
                if (userList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No users found.", color = Color.Gray)
                    }
                } else {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(userList.size) { idx ->
*//*                            UserSuggestionCard(
                                user = userList[idx],
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(160.dp)
                            )*//*
                        }
                    }
                }
            }

            is ApiResult.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (suggestionsState as ApiResult.Error).message,
                        color = Color.Red
                    )
                }
            }
        }*/

        // Recipe feed vertical list
        when (feedState) {
            is ApiResult.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ApiResult.Success -> {
                val recipes = (feedState as ApiResult.Success).data.recipes
                if (recipes.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No recipes found.", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(recipes.size) { index ->
                            RecipeCard(recipe = recipes[index])
                        }
                    }
                }
            }

            is ApiResult.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (feedState as ApiResult.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
