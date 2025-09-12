package com.example.cookit.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cookit.model.UserSuggestion
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.ui.theme.White

@Composable
fun UserSuggestionGrid(
    userList: List<UserSuggestion>,
    modifier: Modifier = Modifier,
    emptyMessage: String = "No items found.",
    header: @Composable (() -> Unit)? = {
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
    },
    itemContent: @Composable (UserSuggestion) -> Unit = { user -> UserSuggestionCard(user) }
) {
    Column(
        modifier = modifier.background(White)
    ) {
        header?.invoke()
        Spacer(modifier = Modifier.height(4.dp))

        if (userList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = emptyMessage,
                    color = Color.Gray
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(userList) { user ->
                    itemContent(user)
                }
            }
        }
    }
}
