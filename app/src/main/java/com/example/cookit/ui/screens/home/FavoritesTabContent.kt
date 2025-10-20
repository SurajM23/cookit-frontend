package com.example.cookit.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cookit.model.AllRecipeResponse
import com.example.cookit.model.ApiResult
import com.example.cookit.utils.NavigationConstants
import com.example.cookit.viewModel.HomeViewModel

@Composable
fun FavoritesTabContent(
    viewModel: HomeViewModel,
    navController: NavController,
    userId: String
) {
    val favoriteState by viewModel.userLikedRecipeState.collectAsState()

    // Trigger data load once when the screen appears
    LaunchedEffect(userId) {
        viewModel.getUserLikedRecipe(userId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (favoriteState) {
            is ApiResult.Loading -> {
                // Show full-screen progress
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            is ApiResult.Error -> {
                val message = (favoriteState as ApiResult.Error).message
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { viewModel.getUserLikedRecipe(userId) }) {
                            Text("Retry")
                        }
                    }
                }
            }

            is ApiResult.Success -> {
                val response = (favoriteState as ApiResult.Success<AllRecipeResponse>).data
                val recipes = response.recipes

                if (recipes.isNullOrEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "You haven’t liked any recipes yet.",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        items(recipes, key = { it._id }) { recipe ->
                            RecipeGridItem2(
                                recipe = recipe,
                                onClick = {
                                    navController.navigate(
                                        NavigationConstants.USER_RECIPE_ROUTE.replace(
                                            "{recipeId}",
                                            recipe._id
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
