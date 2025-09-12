package com.example.cookit.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.ui.theme.White

@Composable
fun FloatingAddButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    iconSize: Dp
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .shadow(8.dp, shape = CircleShape)
            .background(color = White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(48.dp)
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