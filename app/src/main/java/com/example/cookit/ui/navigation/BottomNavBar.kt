package com.example.cookit.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookit.ui.composables.BottomNavItemComposable
import com.example.cookit.ui.composables.FloatingAddButton
import com.example.cookit.ui.theme.PrimaryColor

@Composable
fun BottomNavBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    Surface(
        tonalElevation = 3.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.primary,
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .padding(top = 2.dp)
        ) {
        val baseIconSize = if (maxWidth < 380.dp) 24.dp else 28.dp
        val baseTextSize = if (maxWidth < 380.dp) 9.sp else 10.sp
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
                    BottomNavItemComposable(
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
}