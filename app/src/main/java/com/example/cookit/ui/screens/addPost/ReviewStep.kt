package com.example.cookit.ui.screens.addPost

// java/com/example/cookit/ui/screens/addPost/ReviewStep.kt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReviewStep(
    draft: RecipeDraft,
    onBack: () -> Unit
) {
    Column {
        Text("Review Recipe", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("Title: ${draft.title}")
        Text("Ingredients: ${draft.ingredients.joinToString()}")
        Text("Steps: ${draft.steps.joinToString()}")
        Text("Cook Time: ${draft.cookTime} ${draft.cookTimeUnit}")
        Text("Image: ${draft.imageUri ?: "No image"}")
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = onBack) { Text("Back") }
            Button(onClick = { /* TODO: upload recipe */ }) { Text("Submit") }
        }
    }
}