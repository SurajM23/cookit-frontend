package com.example.cookit.ui.screens.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cookit.ui.screens.home.model.UserSuggestion
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.ui.theme.SecondaryColor
import com.example.cookit.ui.theme.White

@Composable
fun HomeTabContentGrid(
    userList: List<UserSuggestion>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        Box(
            modifier = Modifier
                .background(PrimaryColor)
                .fillMaxWidth()
                .height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Follow chefs. Find recipes. Cook better.",
                style = MaterialTheme.typography.titleMedium,
                color = White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(userList) { user ->
                UserCardGrid(user)
            }
        }
    }
}

@Composable
fun UserCardGrid(user: UserSuggestion) {
    var isFollowing by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(150.dp) // Reduced card width for compact grid
            .height(190.dp)
            .clickable { /* Handle user click */ },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryColor)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Profile image or fallback to initial
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(SecondaryColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (!user.profileImageUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = user.profileImageUrl,
                        contentDescription = "profile image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Text(
                        text = user.name.firstOrNull()?.uppercase() ?: "O",
                        color = White,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleSmall,
                color = White,
                maxLines = 1
            )
            Text(
                text = "@${user.username}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                maxLines = 1
            )
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = { isFollowing = !isFollowing },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = PrimaryColor
                ),
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth(0.8f)
            ) {
                Text(if (isFollowing) "Following" else "Follow")
            }
        }
    }
}