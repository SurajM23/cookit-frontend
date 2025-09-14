package com.example.cookit.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cookit.model.UserSuggestion
import com.example.cookit.ui.theme.PrimaryColor

@Composable
fun UserSuggestionStoryCard(
    user: UserSuggestion,
    modifier: Modifier = Modifier,
    onCardClick: (() -> Unit)? = null,
    ringColor: Color = PrimaryColor
) {
    Column(
        modifier = modifier
            .width(72.dp)
            .padding(vertical = 8.dp)
            .clickable { onCardClick?.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Story ring avatar
        Box(
            modifier = Modifier
                .size(64.dp)
                .border(
                    width = 3.dp,
                    brush = Brush.sweepGradient(
                        listOf(
                            ringColor,
                            ringColor.copy(alpha = 0.5f),
                            ringColor,
                        )
                    ),
                    shape = CircleShape
                )
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (!user.profileImageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = user.profileImageUrl,
                    contentDescription = "profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(PrimaryColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.name.firstOrNull()?.uppercase() ?: "O",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        // Display name
        Text(
            text = user.name.take(10),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            maxLines = 1
        )
        // Username below the display name
        Text(
            text = "@${user.username}",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black,
            maxLines = 1
        )
    }
}