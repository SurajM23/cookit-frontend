package com.example.cookit.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookit.model.AllRecipeResponse
import com.example.cookit.model.ApiResult
import com.example.cookit.model.Recipe
import com.example.cookit.utils.NavigationConstants
import com.example.cookit.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val allRecipeState by viewModel.allRecipeState.collectAsState()

    val gridState = rememberLazyGridState()
    var currentPage by remember { mutableStateOf(1) }
    var allRecipes by remember { mutableStateOf(listOf<Recipe>()) }
    var isEndReached by remember { mutableStateOf(false) }
    var isLoadingMore by remember { mutableStateOf(false) }

    // Initial load
    LaunchedEffect(Unit) {
        currentPage = 1
        isEndReached = false
        allRecipes = emptyList()
        viewModel.getAllRecipe(page = currentPage)
    }

    // Append new data whenever state updates
    LaunchedEffect(allRecipeState) {
        when (allRecipeState) {
            is ApiResult.Success -> {
                val newRecipes =
                    (allRecipeState as ApiResult.Success<AllRecipeResponse>).data.recipes
                if (currentPage == 1) {
                    allRecipes = newRecipes
                } else {
                    allRecipes = allRecipes + newRecipes
                }
                if (newRecipes.isEmpty()) {
                    isEndReached = true
                }
                isLoadingMore = false
            }

            is ApiResult.Error -> {
                isLoadingMore = false
            }

            else -> Unit
        }
    }

    // Infinite scroll
    LaunchedEffect(gridState) {
        snapshotFlow {
            gridState.firstVisibleItemIndex + gridState.layoutInfo.visibleItemsInfo.size
        }.collect { lastVisible ->
            val total = gridState.layoutInfo.totalItemsCount
            if (!isEndReached && !isLoadingMore && lastVisible >= total - 3) {
                isLoadingMore = true
                currentPage++
                viewModel.getAllRecipe(page = currentPage)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            placeholder = { Text("Search recipes...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            singleLine = true
        )

        when (allRecipeState) {
            is ApiResult.Loading -> {
                if (allRecipes.isEmpty()) {
                    // First page loading
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }

            is ApiResult.Error -> {
                if (allRecipes.isEmpty()) {
                    // Show error only if nothing is loaded
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            (allRecipeState as ApiResult.Error).message,
                            color = Color.Red
                        )
                    }
                }
            }

            else -> Unit
        }

        // Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = gridState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            val filteredRecipes = allRecipes.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }

            items(filteredRecipes) { recipe ->
                RecipeGridItem2(recipe = recipe) {
                    navController.navigate(
                        NavigationConstants.USER_RECIPE_ROUTE.replace(
                            "{recipeId}",
                            recipe._id
                        )
                    )
                }
            }

            // Pagination loading indicator
            if (isLoadingMore) {
                item(span = { GridItemSpan(3) }) {
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
fun RecipeGridItem2(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )

        }
    }
}
