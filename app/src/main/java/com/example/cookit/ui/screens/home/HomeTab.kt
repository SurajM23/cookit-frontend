package com.example.cookit.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class HomeTab(val label: String, val icon: ImageVector) {
    Home("Home", Icons.Default.Home),
    Search("Search", Icons.Default.Search),
    Favorites("Favorites", Icons.Default.Favorite),
    Profile("Profile", Icons.Default.Person),
    NewScreen("Add", Icons.Default.Add)
}