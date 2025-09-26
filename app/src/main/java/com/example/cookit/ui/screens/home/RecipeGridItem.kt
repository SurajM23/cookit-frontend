package com.example.cookit.ui.screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cookit.model.Recipe
import com.example.cookit.ui.theme.PrimaryColor

@Composable
fun RecipeGridItem(recipe: Recipe, onClick: () -> Unit) {
    Card(
        onClick = onClick, Modifier
            .fillMaxWidth()
            .background(PrimaryColor)
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .background(PrimaryColor)
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            Text(recipe.title, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun ProfileCountItem(count: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(80.dp)) {
        Text(count.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
        Text(label, fontSize = 13.sp, color = Color.Black)
    }
}
