package com.example.cookit.ui.screens.home.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookit.ui.screens.home.model.BottomNavItem
import com.example.cookit.ui.theme.BackgroundColor
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.ui.theme.SecondaryColor

// Navigation items used for the bottom bar
private val navItems = listOf(
    BottomNavItem("Home", Icons.Filled.Home),
    BottomNavItem("Search", Icons.Filled.Search),
    BottomNavItem("Add", Icons.Filled.Add),
    BottomNavItem("Favorites", Icons.Filled.Favorite),
    BottomNavItem("Profile", Icons.Filled.Person)
)

// Main screen composable
@Composable
fun HomeScreen(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }

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
                0 -> HomeTabContent()
                1 -> SearchTabContent()
                2 -> AddTabContent()
                3 -> FavoritesTabContent()
                4 -> ProfileTabContent()
                else -> HomeTabContent()
            }
        }
    }
}

// Bottom navigation bar composable
@Composable
private fun BottomNavBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(elevation = 20.dp)
            .background(PrimaryColor)
            .padding(top = 2.dp)
    ) {
        val baseIconSize = if (maxWidth < 380.dp) 24.dp else 28.dp
        val baseTextSize = if (maxWidth < 380.dp) 11.sp else 12.sp
        val selectedIconSizeIncrease = 4.dp
        val selectedTextSizeIncrease = 1.sp

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index

                if (index == 2) {
                    // Special floating button for the center Add action
                    Box(
                        modifier = Modifier
                            .height(54.dp)
                            .width(60.dp), // wider for floating effect
                        contentAlignment = Alignment.TopCenter
                    ) {
                        FloatingAddButton(
                            icon = item.icon,
                            label = item.label,
                            onClick = { onItemSelected(index) },
                            iconSize = baseIconSize + 2.dp
                        )
                    }
                } else {
                    // Standard bottom nav item
                    BottomNavItem(
                        icon = item.icon,
                        label = item.label,
                        isSelected = isSelected,
                        onClick = { onItemSelected(index) },
                        iconSize = baseIconSize,
                        selectedIconSizeIncrease = selectedIconSizeIncrease,
                        textSize = baseTextSize,
                        selectedTextSizeIncrease = selectedTextSizeIncrease
                    )
                }
            }
        }
    }
}

// Floating Add button (center, index 2)
@Composable
private fun FloatingAddButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    iconSize: Dp
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.large)
            .background(
                color = BackgroundColor,
                shape = MaterialTheme.shapes.large
            ),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(48.dp) // slightly smaller than box
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = PrimaryColor,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

// Regular bottom navigation item
@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    iconSize: Dp,
    selectedIconSizeIncrease: Dp,
    textSize: androidx.compose.ui.unit.TextUnit,
    selectedTextSizeIncrease: androidx.compose.ui.unit.TextUnit
) {
    // Animate icon size if selected
    val animatedIconSize: Dp by animateDpAsState(
        targetValue = if (isSelected) iconSize + selectedIconSizeIncrease else iconSize,
        animationSpec = tween(durationMillis = 150),
        label = "iconSizeAnimation"
    )
    val animatedTextSizeValue: Float by animateFloatAsState(
        targetValue = if (isSelected) (textSize.value + selectedTextSizeIncrease.value) else textSize.value,
        animationSpec = tween(durationMillis = 150),
        label = "textSizeAnimation"
    )

    Column(
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .clickable { onClick() }
            .width(56.dp), // consistent width for nav items
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) BackgroundColor else SecondaryColor,
            modifier = Modifier.size(animatedIconSize)
        )
        Text(
            text = label,
            fontSize = animatedTextSizeValue.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) BackgroundColor else SecondaryColor,
            maxLines = 1
        )
    }
}

@Composable
fun AddTabContent() {
    Text("Favorites Content")
}
