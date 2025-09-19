package com.example.cookit.ui.screens.addPost

// In file: java/com/example/cookit/ui/screens/addPost/TitleStep.kt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TitleStep(
    draft: RecipeDraft,
    onNext: () -> Unit
) {
    var title by remember { mutableStateOf(draft.title) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Recipe Title", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Enter title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                draft.title = title
                onNext()
            },
            enabled = title.isNotBlank(),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Next")
        }
    }
}