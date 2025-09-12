package com.example.cookit.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cookit.data.utils.NavigationConstants

data class BottomNavItem(val label: String, val icon: ImageVector)

val navItems = listOf(
    BottomNavItem(NavigationConstants.HOME_TAB, Icons.Filled.Home),
    BottomNavItem(NavigationConstants.SEARCH_TAB, Icons.Filled.Search),
    BottomNavItem(NavigationConstants.ADD_RECIPE_SCREEN, Icons.Filled.Add),
    BottomNavItem(NavigationConstants.FAVORITES_TAB, Icons.Filled.Favorite),
    BottomNavItem(NavigationConstants.PROFILE_TAB, Icons.Filled.Person)
)