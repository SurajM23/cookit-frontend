package com.example.cookit.ui.screens.addPost

// java/com/example/cookit/ui/screens/addPost/ImageStep.kt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImageStep(
    draft: RecipeDraft,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    // Placeholder: implement image picker as needed
    Column {
        Text("Add Image", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { /* TODO: launch image picker */ }) {
            Text("Pick Image")
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = onBack) { Text("Back") }
            Button(onClick = onNext) { Text("Next") }
        }
    }
}