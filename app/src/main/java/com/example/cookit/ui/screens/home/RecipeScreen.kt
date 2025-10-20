package com.example.cookit.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookit.model.ApiResult
import com.example.cookit.model.RecipeResponse
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.ui.theme.WarningOrange
import com.example.cookit.ui.theme.White
import com.example.cookit.utils.NavigationConstants
import com.example.cookit.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    navController: NavController,
    recipeId: String,
    viewModel: HomeViewModel,
    onBack: () -> Unit,
    onLikeClick: (String) -> Unit
) {
    val recipeState by viewModel.recipeState.collectAsState()
    val likedResponse by viewModel.recipeLikedResponse.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.getRecipeById(recipeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { paddingValues -> 
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (recipeState) {
                is ApiResult.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                is ApiResult.Error -> {
                    Text(
                        (recipeState as ApiResult.Error).message,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                is ApiResult.Success<*> -> {
                    val recipe = (recipeState as ApiResult.Success<RecipeResponse>).data.recipe
                    val isLiked = remember { mutableStateOf(recipe.likes.isNotEmpty()) }
                    val likeCount = remember { mutableStateOf(recipe.likeCount) }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        // Top image
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(260.dp)
                            ) {
                                AsyncImage(
                                    model = recipe.imageUrl,
                                    contentDescription = recipe.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.matchParentSize()
                                )

                                // Floating like button over image
                                IconButton(
                                    onClick = {
                                        isLiked.value = !isLiked.value
                                        likeCount.value += if (isLiked.value) 1 else -1
                                        onLikeClick(recipe._id)
                                    },
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(16.dp)
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surface)
                                ) {
                                    Icon(
                                        imageVector = if (isLiked.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Like",
                                        tint = if (isLiked.value) Color.Red else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }

                        // Recipe content card
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Column(
                                    Modifier
                                        .padding(16.dp)
                                        .background(MaterialTheme.colorScheme.surface)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        // Row 1: Title + Cook Time
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = recipe.title,
                                                style = MaterialTheme.typography.headlineSmall.copy(
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                color = MaterialTheme.colorScheme.onSurface,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis, // prevents overlap
                                                modifier = Modifier.weight(1f)
                                            )

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.DateRange,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Text(
                                                    text = "${recipe.cookTime} min",
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }

                                        // Row 2: Author + Likes
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // Author chip
                                            Card(
                                                onClick = {
                                                    navController.navigate(
                                                        NavigationConstants.USER_PROFILE_ROUTE.replace(
                                                            "{userId}",
                                                            recipe.author._id
                                                        )
                                                    )
                                                },
                                                shape = RoundedCornerShape(50),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = PrimaryColor
                                                )
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.padding(
                                                        horizontal = 8.dp,
                                                        vertical = 4.dp
                                                    )
                                                ) {
                                                    AsyncImage(
                                                        model = recipe.author.avatarUrl,
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .size(28.dp)
                                                            .clip(CircleShape)
                                                    )
                                                    Spacer(Modifier.width(6.dp))
                                                    Text(
                                                        text = recipe.author.name,
                                                        fontWeight = FontWeight.Medium,
                                                        color = MaterialTheme.colorScheme.onPrimary
                                                    )
                                                }
                                            }

                                            // Likes
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Icon(
                                                    imageVector = if (isLiked.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                                    contentDescription = null,
                                                    tint = if (isLiked.value) Color.Red else MaterialTheme.colorScheme.onSurface,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Text(
                                                    text = "${likeCount.value} likes",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }


                                    Spacer(Modifier.height(8.dp))

                                    // Description
                                    Text(
                                        recipe.description,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Spacer(Modifier.height(18.dp))

                                    // Ingredients
                                    Text(
                                        "Ingredients",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = PrimaryColor
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        items(recipe.ingredients) { ingredient ->
                                            AssistChip(
                                                onClick = {},
                                                label = { Text(ingredient) },
                                                colors = AssistChipDefaults.assistChipColors(
                                                    containerColor = WarningOrange,
                                                    labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                                ),
                                                modifier = Modifier.height(36.dp)
                                            )
                                        }
                                    }

                                    Spacer(Modifier.height(18.dp))

                                    // Steps
                                    Text(
                                        "Steps",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = PrimaryColor
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Column {
                                        recipe.steps.forEachIndexed { index, step ->
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(vertical = 6.dp)
                                            ) {
                                                Surface(
                                                    shape = CircleShape,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier
                                                        .size(28.dp)
                                                        .background(PrimaryColor)
                                                ) {
                                                    Box(
                                                        contentAlignment = Alignment.Center,
                                                        modifier = Modifier.background(PrimaryColor)
                                                    ) {
                                                        Text(
                                                            "${index + 1}",
                                                            color = White,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                                Spacer(Modifier.width(12.dp))
                                                Text(
                                                    step,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(28.dp)) }
                    }
                }
            }
        }
    }
}
