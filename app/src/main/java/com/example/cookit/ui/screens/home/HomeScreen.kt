package com.example.cookit.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)

val navItems = listOf(
    BottomNavItem("Home", Icons.Filled.Home),
    BottomNavItem("Search", Icons.Filled.Search),
    BottomNavItem("Add", Icons.Filled.Add),   // Middle tab
    BottomNavItem("Favorites", Icons.Filled.Favorite),
    BottomNavItem("Profile", Icons.Filled.Person)
)

@Composable
fun HomeScreen(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Scaffold(
        bottomBar = {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                val iconSize = if (maxWidth < 360.dp) 20.dp else 28.dp
                val textSize = if (maxWidth < 360.dp) 10.sp else 12.sp

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItems.forEachIndexed { index, item ->
                        if (index == 2) {
                            // Middle tab: special decoration, triggers new screen
                            IconButton(
                                onClick = { navController.navigate("newscreen") },
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.secondary,
                                        shape = MaterialTheme.shapes.large
                                    )
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                    tint = Color.White,
                                    modifier = Modifier.size(iconSize)
                                )
                            }
                        } else {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 2.dp)
                                    .clickable { selectedIndex = index }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                    tint = if (selectedIndex == index) Color.White else Color.Gray,
                                    modifier = Modifier.size(iconSize)
                                )
                                Text(
                                    text = item.label,
                                    fontSize = textSize,
                                    color = if (selectedIndex == index) Color.White else Color.Gray,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Switch UI based on selected tab (except middle tab)
            when (selectedIndex) {
                0 -> HomeTabContent()
                1 -> SearchTabContent()
                2 -> AddTabContent()
                3 -> FavoritesTabContent()
                4 -> ProfileTabContent()
            }
        }
    }
}

@Composable
fun HomeTabContent() {
    Text("Home Content")
}

@Composable
fun SearchTabContent() {
    Text("Search Content")
}


@Composable
fun AddTabContent() {
    Text("Favorites Content")
}


@Composable
fun FavoritesTabContent() {
    Text("Favorites Content")
}

@Composable
fun ProfileTabContent() {
    Text("Profile Content")
}