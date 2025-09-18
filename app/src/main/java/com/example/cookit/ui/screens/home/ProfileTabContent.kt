package com.example.cookit.ui.screens.home

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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookit.model.ApiResult
import com.example.cookit.model.Recipe
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.SimpleMessageResponse
import com.example.cookit.model.UserProfile
import com.example.cookit.ui.composables.CookitActionButton
import com.example.cookit.ui.composables.RecipeCard
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.utils.NavigationConstants
import com.example.cookit.utils.PrefManager
import com.example.cookit.viewModel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.User

@Composable
fun ProfileContent(
    navController: NavController,
    viewModel: HomeViewModel,
    isCurrentUser: Boolean = false,
    onActionClick: (String) -> Unit = {}
) {

    val profileState by viewModel.profileState.collectAsState()
    val feedState by viewModel.feedState2.collectAsState()
    val followAction by viewModel.followResponse.collectAsState()

    val gridState = rememberLazyGridState()
    var currentPage by rememberSaveable { mutableStateOf(1) }
    var isEndReached by rememberSaveable { mutableStateOf(false) }
    var allRecipes by rememberSaveable { mutableStateOf(listOf<Recipe>()) }
    var action by rememberSaveable { mutableStateOf("follow") }

    val isRefreshing = profileState is ApiResult.Loading || feedState is ApiResult.Loading

    // Initial load
    LaunchedEffect(PrefManager.getUserId() ?: "") {
        currentPage = 1
        isEndReached = false
        viewModel.getUserProfile(PrefManager.getUserId() ?: "")
        viewModel.getUserRecipes(PrefManager.getUserId() ?: "", 1)
    }

    // Recipes update
    LaunchedEffect(feedState) {
        if (feedState is ApiResult.Success) {
            val newRecipes = (feedState as ApiResult.Success<RecipeFeedResponse>).data.recipes
            allRecipes = if (currentPage == 1) newRecipes else allRecipes + newRecipes
            if (newRecipes.isEmpty()) isEndReached = true
        }
    }

    // Follow/Unfollow update
    LaunchedEffect(followAction) {
        if (followAction is ApiResult.Success) {
            val msg = (followAction as ApiResult.Success<SimpleMessageResponse>).data.message
            action = if (msg.contains("unfollow")) "follow" else "unfollow"
        }
    }

    // Infinite scroll
    LaunchedEffect(gridState, allRecipes, isEndReached) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItem ->
                if (!isEndReached &&
                    lastVisibleItem == (allRecipes.size - 3) &&
                    feedState !is ApiResult.Loading
                ) {
                    currentPage++
                    viewModel.getUserRecipes(PrefManager.getUserId() ?: "", currentPage)
                }
            }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            currentPage = 1
            isEndReached = false
            viewModel.getUserProfile(PrefManager.getUserId() ?: "")
            viewModel.getUserRecipes(PrefManager.getUserId() ?: "", 1)
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
            item(span = { GridItemSpan(2) }) {
                ProfileHeaderSection(
                    profileState = profileState,
                    postCount = allRecipes.size,
                    isCurrentUser = isCurrentUser,
                    action = action,
                    onActionClick = { onActionClick(action) }
                )
            }

            items(allRecipes) { recipe ->
                RecipeGridItem(recipe, {
                    navController.navigate(
                        NavigationConstants.USER_RECIPE_ROUTE.replace(
                            "{recipeId}",
                            recipe._id
                        )
                    )
                })
            }

            if (feedState is ApiResult.Loading && currentPage > 1) {
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier
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
