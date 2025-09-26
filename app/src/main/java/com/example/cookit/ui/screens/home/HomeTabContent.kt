package com.example.cookit.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cookit.model.ApiResult
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.UserSuggestion
import com.example.cookit.ui.composables.RecipeCard
import com.example.cookit.ui.composables.UserSuggestionStoryCard
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.utils.NavigationConstants
import com.example.cookit.viewModel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun HomeTabContent(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val suggestionsState by homeViewModel.userSuggestions.collectAsState()
    val feedState by homeViewModel.feedState.collectAsState()

    var currentPage by remember { mutableIntStateOf(1) }
    var endReached by remember { mutableStateOf(false) }
    var isLoadingMore by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    val isRefreshing = feedState is ApiResult.Loading && currentPage == 1

    // Initial load
    LaunchedEffect(Unit) {
        if (feedState !is ApiResult.Success) {
            homeViewModel.getRecipeFeed(page = currentPage)
        }
        if (suggestionsState !is ApiResult.Success) {
            homeViewModel.getUserSuggestions()
        }
    }

    // Pagination: trigger when user scrolls near bottom
    LaunchedEffect(listState, feedState) {
        snapshotFlow {
            listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size
        }.distinctUntilChanged()
            .collect { lastVisible ->
                val total = listState.layoutInfo.totalItemsCount
                val totalPages =
                    if (feedState is ApiResult.Success) (feedState as ApiResult.Success<RecipeFeedResponse>).data.totalPages
                    else Int.MAX_VALUE

                if (!endReached && !isLoadingMore && lastVisible >= total - 3 && currentPage < totalPages) {
                    isLoadingMore = true
                    currentPage++
                    homeViewModel.getRecipeFeed(page = currentPage)
                    if (currentPage >= totalPages) endReached = true
                    isLoadingMore = false
                }
            }
    }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    currentPage = 1
                    endReached = false
                    homeViewModel.getRecipeFeed(page = 1)
                    homeViewModel.getUserSuggestions()
                }
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(4.dp, 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Suggestions Row
                    item {
                        SuggestionsSectionHeader()
                        ShowUserSuggestionsRow(navController, suggestionsState)
                    }

                    // Feed items
                    when (feedState) {
                        is ApiResult.Success -> {
                            val recipes =
                                (feedState as ApiResult.Success<RecipeFeedResponse>).data.recipes

                            items(recipes) { recipe ->
                                RecipeCard(recipe) {
                                    navController.navigate(
                                        NavigationConstants.USER_RECIPE_ROUTE.replace(
                                            "{recipeId}", recipe._id
                                        )
                                    )
                                }
                            }

                            // Loader for pagination
                            if (isLoadingMore && currentPage > 1) {
                                item {
                                    Box(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) { CircularProgressIndicator() }
                                }
                            }
                        }

                        is ApiResult.Error -> {
                            item {
                                ErrorBox(
                                    message = (feedState as ApiResult.Error).message,
                                    onUnauthorized = {
                                        navController.navigate(NavigationConstants.LOGIN_SCREEN) {
                                            popUpTo(NavigationConstants.HOME_SCREEN) {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    onRetry = {
                                        homeViewModel.getRecipeFeed(page = currentPage)
                                    }
                                )
                            }
                        }

                        is ApiResult.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = PrimaryColor)
                                }
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }
}

@Composable
private fun SuggestionsSectionHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = "Suggestions",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ShowUserSuggestionsRow(
    navController: NavController,
    suggestionsState: ApiResult<List<UserSuggestion>>
) {
    when (suggestionsState) {
        is ApiResult.Success -> {
            val users = suggestionsState.data
            if (users.isEmpty()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No users found.", color = Color.Gray)
                }
            } else {
                LazyRow(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(users) { user ->
                        UserSuggestionStoryCard(user) {
                            navController.navigate(
                                NavigationConstants.USER_PROFILE_ROUTE.replace("{userId}", user._id)
                            )
                        }
                    }
                }
            }
        }

        is ApiResult.Error -> {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ) { Text(suggestionsState.message, color = Color.Red) }
        }

        else -> Unit
    }
}

@Composable
fun ErrorBox(message: String, onUnauthorized: () -> Unit, onRetry: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        if (onRetry != null) {
            androidx.compose.material3.TextButton(onClick = { onRetry() }) {
                Text("Retry")
            }
        }
    }

    if (message.contains("Unauthorized", ignoreCase = true)) {
        onUnauthorized()
    }
}
