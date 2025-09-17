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
import coil.compose.AsyncImage
import com.example.cookit.model.ApiResult
import com.example.cookit.model.Recipe
import com.example.cookit.model.RecipeFeedResponse
import com.example.cookit.model.UserProfile
import com.example.cookit.viewModel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.User

@Composable
fun ProfileTabContent(
    token: String,
    userId: String,
    viewModel: HomeViewModel
) {
    val profileState by viewModel.profileState.collectAsState()
    val feedState by viewModel.feedState2.collectAsState()
    val gridState = rememberLazyGridState()
    var currentPage by rememberSaveable { mutableStateOf(1) }
    var isEndReached by rememberSaveable { mutableStateOf(false) }
    var allRecipes by rememberSaveable { mutableStateOf(listOf<Recipe>()) }

    // Initial load
    LaunchedEffect(userId) {
        if (profileState !is ApiResult.Success) {
            viewModel.getUserProfile(token, userId)
            currentPage = 1
            isEndReached = false
            viewModel.getRecipeFeed(token, userId, 1)
        }
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
                .padding(horizontal = 8.dp),
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
                        ProfileHeader(profile, postCount = allRecipes.size)
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


@Composable
fun ProfileHeader(profile: UserProfile, postCount: Int = 0, isCurrentUser: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 24.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(92.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (profile.avatarUrl.isEmpty()) {
                // Show user icon if URL is empty
                Icon(
                    imageVector = FontAwesomeIcons.Regular.User,
                    contentDescription = "Default User Icon",
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .padding(20.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            } else {
                // Load image if URL is not empty
                AsyncImage(
                    model = profile.avatarUrl,
                    contentDescription = "Profile Avatar",
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }


        Spacer(Modifier.height(10.dp))
        // Username and name
        Text(
            text = profile.name,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 20.sp
        )
        Text(
            text = "@${profile.username}",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
            fontSize = 15.sp
        )

        Spacer(Modifier.height(8.dp))

        // Counts row: Posts, Followers, Following
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileCountItem(count = postCount, label = "Posts")
            ProfileCountItem(count = profile.followersCount, label = "Followers")
            ProfileCountItem(count = profile.followingCount, label = "Following")
        }

        Spacer(Modifier.height(8.dp))

        // Bio
        if (profile.bio.isNotEmpty()) {
            Text(
                text = profile.bio,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }

        Spacer(Modifier.height(14.dp))

    }
}

// Helper for count display
@Composable
fun ProfileCountItem(count: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp
        )
    }
}

@Composable
fun RecipeGridItem(recipe: Recipe) {
    // Replace with your own recipe card/grid design
    Card(
        Modifier
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            Text(recipe.title, style = MaterialTheme.typography.titleMedium)
            // Add more recipe info as desired
        }
    }
}