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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.cookit.model.ApiResult
import com.example.cookit.model.UserSuggestion
import com.example.cookit.ui.composables.RecipeCard
import com.example.cookit.ui.composables.UserSuggestionCard
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.utils.PrefManager
import com.example.cookit.viewModel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*

@Composable
fun HomeTabContent(
    context: Context,
    homeViewModel: HomeViewModel
) {
    val suggestionsState by homeViewModel.userSuggestions.collectAsState()
    val feedState by homeViewModel.feedState.collectAsState()
    val isLoadingFeed by remember { derivedStateOf { feedState is ApiResult.Loading } }
    val isLoadingSuggestions by remember { derivedStateOf { suggestionsState is ApiResult.Loading } }

    // For pagination
    var currentPage by remember { mutableIntStateOf(1) }
    var endReached by remember { mutableStateOf(false) }

    // Fetch recipe feed only if not present
    LaunchedEffect(Unit) {
        if (feedState !is ApiResult.Success) {
            homeViewModel.getRecipeFeed(PrefManager.getInstance(context).getToken(), page = currentPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Pull-to-refresh for recipes
        SwipeRefresh(
            state = rememberSwipeRefreshState(isLoadingFeed),
            onRefresh = {
                currentPage = 1
                endReached = false
                homeViewModel.getRecipeFeed(PrefManager.getInstance(context).getToken(), page = 1)
            }
        ) {
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
                    val totalPages = (feedState as ApiResult.Success).data.totalPages

                    if (recipes.isEmpty()) {
                        // Pull-to-refresh for user suggestions grid
                        SwipeRefresh(
                            state = rememberSwipeRefreshState(isLoadingSuggestions),
                            onRefresh = {
                                homeViewModel.getUserSuggestions(
                                    PrefManager.getInstance(context).getToken() ?: ""
                                )
                            }
                        ) {
                            ShowUserSuggestionsGrid(suggestionsState)
                        }
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No recipes found.", color = Color.Gray)
                        }

                        // Fetch user suggestions if not loaded
                        LaunchedEffect(Unit) {
                            if (suggestionsState !is ApiResult.Success) {
                                homeViewModel.getUserSuggestions(PrefManager.getInstance(context).getToken() ?: "")
                            }
                        }
                    } else {
                        // Pagination logic
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            itemsIndexed(recipes) { index, recipe ->
                                RecipeCard(recipe = recipe)

                                // If we're near the end, load more recipes
                                if (index == recipes.lastIndex - 2 && !endReached && currentPage < totalPages) {
                                    LaunchedEffect(Unit) {
                                        currentPage += 1
                                        homeViewModel.getRecipeFeed(
                                            PrefManager.getInstance(context).getToken(),
                                            page = currentPage
                                        )
                                        if (currentPage >= totalPages) {
                                            endReached = true
                                        }
                                    }
                                }
                            }
                            // Show loading at bottom if paginating
                            if (isLoadingFeed && currentPage > 1) {
                                item {
                                    Box(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
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
}

@Composable
fun ShowUserSuggestionsGrid(suggestionsState: ApiResult<List<UserSuggestion>>) {
    when (suggestionsState) {
        is ApiResult.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryColor)
            }
        }

        is ApiResult.Success -> {
            val userList = suggestionsState.data
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(userList) { user ->
                        UserSuggestionCard(
                            user = user,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        )
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
                    text = suggestionsState.message,
                    color = Color.Red
                )
            }
        }
    }
}