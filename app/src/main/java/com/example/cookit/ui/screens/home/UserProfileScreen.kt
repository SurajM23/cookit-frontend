package com.example.cookit.ui.screens.home


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cookit.model.ApiResult
import com.example.cookit.model.Recipe
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.UserProfile
import com.example.cookit.ui.composables.CookitActionButton
import com.example.cookit.ui.composables.CookitTextButton
import com.example.cookit.utils.PrefManager
import com.example.cookit.viewModel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.User

@Composable
fun UserProfileScreen(
    context: Context,
    userId: String,
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {
    val token = com.example.cookit.utils.PrefManager.getInstance(context).getToken() ?: ""
    val profileState by viewModel.profileState.collectAsState()
    val feedState by viewModel.feedState2.collectAsState()
    val gridState = rememberLazyGridState()
    var currentPage by rememberSaveable { mutableStateOf(1) }
    var isEndReached by rememberSaveable { mutableStateOf(false) }
    var allRecipes by rememberSaveable { mutableStateOf(listOf<Recipe>()) }

    // Initial load
    LaunchedEffect(userId) {
        currentPage = 1
        isEndReached = false
        viewModel.getUserProfile(token, userId)   // always load profile
        viewModel.getRecipeFeed(token, userId, 1) // always load first page
    }

    // Collect and append recipes for infinite scroll
    LaunchedEffect(feedState) {
        if (feedState is ApiResult.Success) {
            val newRecipes = (feedState as ApiResult.Success<RecipeFeedResponse>).data.recipes
            if (currentPage == 1) {
                allRecipes = newRecipes
            } else {
                if (newRecipes.isEmpty()) {
                    isEndReached = true
                } else {
                    allRecipes = allRecipes + newRecipes
                }
            }
        }
    }

    // Infinite scroll: load next page when near end
    LaunchedEffect(gridState, allRecipes, isEndReached) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItem ->
                if (
                    !isEndReached &&
                    lastVisibleItem == (allRecipes.size - 3) &&
                    feedState !is ApiResult.Loading
                ) {
                    currentPage += 1
                    viewModel.getRecipeFeed(token, userId, currentPage)
                }
            }
    }

    val isRefreshing = feedState is ApiResult.Loading || profileState is ApiResult.Loading

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            currentPage = 1
            isEndReached = false
            viewModel.getUserProfile(token, userId)
            viewModel.getRecipeFeed(token, userId, 1)
        }
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .background(Color.White),
            state = gridState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header as first full-span item
            item(span = { GridItemSpan(2) }) {
                when (profileState) {
                    is ApiResult.Loading -> {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is ApiResult.Error -> {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text((profileState as ApiResult.Error).message)
                        }
                    }

                    is ApiResult.Success -> {
                        val profile = (profileState as ApiResult.Success<UserProfile>).data
                            ProfileHeader(profile, postCount = allRecipes.size,true)
                            Spacer(Modifier.height(8.dp))
                    }
                }
            }

            // User posts (grid items)
            items(allRecipes) { recipe ->
                RecipeGridItem(recipe)
            }

            // Loading indicator at bottom
            if (feedState is ApiResult.Loading && currentPage > 1) {
                item(span = { GridItemSpan(2) }) {
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