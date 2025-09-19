package com.example.cookit.ui.screens.addPost

// java/com/example/cookit/ui/screens/addPost/StepsStep.kt
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StepsStep(
    draft: RecipeDraft,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var steps by remember { mutableStateOf(draft.steps.toMutableList()) }
    var newStep by remember { mutableStateOf("") }

    Column {
        Text("Steps", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        steps.forEachIndexed { idx, step ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${idx + 1}. $step")
                IconButton(onClick = { steps.removeAt(idx) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                }
            }
        }
        OutlinedTextField(
            value = newStep,
            onValueChange = { newStep = it },
            label = { Text("Add step") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (newStep.isNotBlank()) {
                    steps.add(newStep)
                    newStep = ""
                }
            },
            enabled = newStep.isNotBlank(),
            modifier = Modifier.padding(top = 8.dp)
        ) { Text("Add") }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = onBack) { Text("Back") }
            Button(
                onClick = {
                    draft.steps = steps
                    onNext()
                },
                enabled = steps.isNotEmpty()
            ) { Text("Next") }
        }
    }
}