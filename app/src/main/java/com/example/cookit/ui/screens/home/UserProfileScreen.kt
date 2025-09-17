package com.example.cookit.ui.screens.home


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cookit.model.ApiResult
import com.example.cookit.model.Recipe
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.SimpleMessageResponse
import com.example.cookit.model.UserProfile
import com.example.cookit.utils.PrefManager
import com.example.cookit.viewModel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun UserProfileScreen(
    context: Context,
    userId: String,
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {
    val profileState by viewModel.profileState.collectAsState()
    val feedState by viewModel.feedState2.collectAsState()
    val followAction by viewModel.followResponse.collectAsState()

    val gridState = rememberLazyGridState()
    var currentPage by rememberSaveable { mutableStateOf(1) }
    var isEndReached by rememberSaveable { mutableStateOf(false) }
    var allRecipes by rememberSaveable { mutableStateOf(listOf<Recipe>()) }
    var action by rememberSaveable { mutableStateOf("follow") }

    // Initial load
    LaunchedEffect(userId) {
        currentPage = 1
        isEndReached = false
        viewModel.getUserProfile(userId)
        viewModel.getUserRecipes(PrefManager.getUserId() ?: "",1)
    }

    // Append new recipes
    LaunchedEffect(feedState) {
        if (feedState is ApiResult.Success) {
            val newRecipes = (feedState as ApiResult.Success<RecipeFeedResponse>).data.recipes
            allRecipes = if (currentPage == 1) newRecipes else allRecipes + newRecipes
            if (newRecipes.isEmpty()) isEndReached = true
        }
    }

    // Update follow/unfollow button
    LaunchedEffect(followAction) {
        if (followAction is ApiResult.Success) {
            action = if ((followAction as ApiResult.Success<SimpleMessageResponse>).data.message
                    .contains("unfollowed", true)
            ) "follow" else "unfollow"
        }
    }

    // Infinite scroll
    LaunchedEffect(gridState, allRecipes, isEndReached) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItem ->
                if (!isEndReached &&
                    lastVisibleItem == allRecipes.size - 3 &&
                    feedState !is ApiResult.Loading
                ) {
                    currentPage++
                    viewModel.getUserRecipes(PrefManager.getUserId() ?: "",currentPage)
                }
            }
    }

    val isRefreshing = feedState is ApiResult.Loading || profileState is ApiResult.Loading

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            currentPage = 1
            isEndReached = false
            viewModel.getUserProfile(userId)
            viewModel.getUserRecipes(PrefManager.getUserId() ?: "",1)
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .background(Color.White)
        ) {
            // Header
            item(span = { GridItemSpan(2) }) {
                when (profileState) {
                    is ApiResult.Loading -> CircularProgressBox()
                    is ApiResult.Error -> Text(
                        (profileState as ApiResult.Error).message,
                        modifier = Modifier.padding(16.dp)
                    )

                    is ApiResult.Success -> {
                        val profile = (profileState as ApiResult.Success<UserProfile>).data
                        action = if (profile.followers.contains(
                                PrefManager.getUserId()
                            )
                        ) "unfollow" else "follow"

                        ProfileHeader(
                            profile = profile,
                            postCount = allRecipes.size,
                            isCurrentUser = true,
                            action = action,
                            onActionClick = { viewModel.followUser(userId, action == "follow") }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }

            // Recipes
            items(allRecipes) { recipe -> RecipeGridItem(recipe) }

            // Pagination loading
            if (feedState is ApiResult.Loading && currentPage > 1) {
                item(span = { GridItemSpan(2) }) {
                    CircularProgressBox()
                }
            }
        }
    }
}

@Composable
fun CircularProgressBox() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
