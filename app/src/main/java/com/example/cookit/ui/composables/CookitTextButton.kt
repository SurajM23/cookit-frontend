package com.example.cookit.ui.composables


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CookitTextButton(
    message: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    style: TextStyle = MaterialTheme.typography.labelLarge
) {
    TextButton(onClick = onClick) {
        Text(
            text = message,
            color = color,
            style = style,
            modifier = modifier.padding(start = 16.dp, top = 2.dp)
        )
    }
}