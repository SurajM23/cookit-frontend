package com.example.cookit.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.cookit.ui.navigation.BottomNavBar
import com.example.cookit.utils.NavigationConstants
import com.example.cookit.utils.PrefManager
import com.example.cookit.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    selectedIndex = index
                    if (index == 2) navController.navigate(NavigationConstants.ADD_RECIPE_SCREEN)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (selectedIndex) {
                0 -> HomeTabContent(navController,  homeViewModel)
                1 -> ExploreScreen(navController,homeViewModel)
                3 -> FavoritesTabContent(
                    navController = navController,
                    viewModel = homeViewModel,
                    userId = PrefManager.getUserId() ?: ""
                )
                4 -> ProfileContent(navController,viewModel = homeViewModel)
                else -> HomeTabContent(navController,homeViewModel)
            }
        }
    }
}


