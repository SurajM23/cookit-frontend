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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.cookit.utils.PrefManager
import com.example.cookit.viewModel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun HomeTabContent(
    context: Context,
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    val suggestionsState by homeViewModel.userSuggestions.collectAsState()
    val feedState by homeViewModel.feedState.collectAsState()
    val isLoadingFeed by remember { derivedStateOf { feedState is ApiResult.Loading } }
    val isLoadingSuggestions by remember { derivedStateOf { suggestionsState is ApiResult.Loading } }

    // For pagination
    var currentPage by remember { mutableIntStateOf(1) }
    var endReached by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (feedState !is ApiResult.Success) {
            homeViewModel.getRecipeFeed(
                PrefManager.getInstance(context).getToken(),
                page = currentPage
            )
        }
        if (suggestionsState !is ApiResult.Success) {
            homeViewModel.getUserSuggestions(PrefManager.getInstance(context).getToken() ?: "")
        }
    }

    val isRefreshing =
        isLoadingFeed && currentPage == 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                currentPage = 1
                endReached = false
                homeViewModel.getRecipeFeed(PrefManager.getInstance(context).getToken(), page = 1)
                homeViewModel.getUserSuggestions(PrefManager.getInstance(context).getToken() ?: "")
            }
        ) {
            when {
                isLoadingSuggestions -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryColor)
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(4.dp,12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            ShowUserSuggestionsRow(
                                navController = navController,
                                suggestionsState = suggestionsState,
                            )
                        }
                        when (feedState) {
                            is ApiResult.Success -> {
                                val recipes =
                                    (feedState as ApiResult.Success<RecipeFeedResponse>).data.recipes
                                val totalPages =
                                    (feedState as ApiResult.Success<RecipeFeedResponse>).data.totalPages

                                itemsIndexed(recipes) { index, recipe ->
                                    RecipeCard(recipe = recipe)

                                    // Pagination: Load more recipes when near end
                                    if (index == recipes.lastIndex - 2 && !endReached && currentPage < totalPages) {
                                        LaunchedEffect(currentPage) {
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

                            is ApiResult.Error -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 32.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = (feedState as ApiResult.Error).message,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                                if ((feedState as ApiResult.Error).message.contains(
                                        "Unauthorized",
                                        ignoreCase = true
                                    )
                                ) {
                                    navController.navigate(NavigationConstants.LOGIN_SCREEN) {
                                        popUpTo(NavigationConstants.HOME_SCREEN) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }

                            else -> { /* Do nothing */
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowUserSuggestionsRow(
    navController: NavController,
    suggestionsState: ApiResult<List<UserSuggestion>>,
) {
    when (suggestionsState) {
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
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(userList) { user ->
                        UserSuggestionStoryCard(user, onCardClick = {
                            navController.navigate(NavigationConstants.USER_PROFILE_ROUTE)
                        })
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

        else -> {}
    }
}