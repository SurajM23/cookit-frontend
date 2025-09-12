package com.example.cookit.ui.screens.home

import android.content.Context
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookit.data.network.HomeRepository
import com.example.cookit.data.network.RetrofitInstance
import com.example.cookit.ui.navigation.BottomNavBar
import com.example.cookit.viewModel.HomeViewModel
import com.example.cookit.viewModel.HomeViewModelFactory

// Main screen composable
@Composable
fun HomeScreen(context: Context, navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(HomeRepository(RetrofitInstance.api)))
    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    selectedIndex = index
                    if (index == 2) navController.navigate("add_screen_route")
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Show the selected tab's content
            when (selectedIndex) {
                0 -> HomeTabContent(context,homeViewModel)
                1 -> SearchTabContent()
                2 -> AddTabContent()
                3 -> FavoritesTabContent()
                4 -> ProfileTabContent()
                else -> HomeTabContent(context,homeViewModel)
            }
        }
    }
}

@Composable
fun AddTabContent() {
    // Placeholder for Add tab content
}
