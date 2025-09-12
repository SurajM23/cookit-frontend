package com.example.cookit.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cookit.model.Recipe

@Composable
fun RecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(recipe.title, style = MaterialTheme.typography.titleMedium)
            Text("By: ${recipe.author.name}", style = MaterialTheme.typography.bodySmall)
            Text("Cook time: ${recipe.cookTime} min", style = MaterialTheme.typography.bodySmall)
        }
    }
}