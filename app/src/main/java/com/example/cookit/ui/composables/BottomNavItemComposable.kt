package com.example.cookit.ui.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookit.ui.theme.SecondaryColor
import com.example.cookit.ui.theme.White


@Composable
fun BottomNavItemComposable(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    iconSize: Dp,
    selectedIconSizeIncrease: Dp,
    textSize: TextUnit,
    selectedTextSizeIncrease: TextUnit,
    shape: Shape = MaterialTheme.shapes.medium
) {
    val animatedIconSize by animateDpAsState(
        targetValue = if (isSelected) iconSize + selectedIconSizeIncrease else iconSize,
        animationSpec = tween(durationMillis = 150),
        label = "iconSizeAnimation"
    )
    val animatedTextSizeValue by animateFloatAsState(
        targetValue = if (isSelected) (textSize.value + selectedTextSizeIncrease.value) else textSize.value,
        animationSpec = tween(durationMillis = 150),
        label = "textSizeAnimation"
    )

    Surface(
        shape = shape,
        tonalElevation = if (isSelected) 6.dp else 0.dp,
        color = Color.Transparent,
        modifier = Modifier
            .width(56.dp)
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) White else SecondaryColor,
                modifier = Modifier.size(animatedIconSize)
            )
            Text(
                text = label,
                fontSize = animatedTextSizeValue.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) White else SecondaryColor,
                maxLines = 1
            )
        }
    }
}
