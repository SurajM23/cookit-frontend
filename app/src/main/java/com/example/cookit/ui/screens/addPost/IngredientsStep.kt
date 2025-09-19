package com.example.cookit.ui.screens.addPost

// java/com/example/cookit/ui/screens/addPost/IngredientsStep.kt
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IngredientsStep(
    draft: RecipeDraft,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var ingredients by remember { mutableStateOf(draft.ingredients.toMutableList()) }
    var newIngredient by remember { mutableStateOf("") }

    Column {
        Text("Ingredients", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        ingredients.forEachIndexed { idx, ingredient ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(ingredient)
                IconButton(onClick = { ingredients.removeAt(idx) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                }
            }
        }
        OutlinedTextField(
            value = newIngredient,
            onValueChange = { newIngredient = it },
            label = { Text("Add ingredient") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (newIngredient.isNotBlank()) {
                    ingredients.add(newIngredient)
                    newIngredient = ""
                }
            },
            enabled = newIngredient.isNotBlank(),
            modifier = Modifier.padding(top = 8.dp)
        ) { Text("Add") }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = onBack) { Text("Back") }
            Button(
                onClick = {
                    draft.ingredients = ingredients
                    onNext()
                },
                enabled = ingredients.isNotEmpty()
            ) { Text("Next") }
        }
    }
}