package com.example.cookit.ui.screens.addPost

// java/com/example/cookit/ui/screens/addPost/CookTimeStep.kt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CookTimeStep(
    draft: RecipeDraft,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var cookTime by remember { mutableStateOf(draft.cookTime) }
    var unit by remember { mutableStateOf(draft.cookTimeUnit) }
    val units = listOf("min", "hr")

    Column {
        Text("Cook Time", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = cookTime,
            onValueChange = { cookTime = it.filter { c -> c.isDigit() } },
            label = { Text("Time") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Row {
            units.forEach { u ->
                OutlinedButton(
                    onClick = { unit = u },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (unit == u) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                    )
                ) { Text(u) }
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = onBack) { Text("Back") }
            Button(
                onClick = {
                    draft.cookTime = cookTime
                    draft.cookTimeUnit = unit
                    onNext()
                },
                enabled = cookTime.isNotBlank()
            ) { Text("Next") }
        }
    }
}