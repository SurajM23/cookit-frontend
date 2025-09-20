package com.example.cookit.ui.screens.addPost

// In file: java/com/example/cookit/ui/screens/addPost/TitleStep.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cookit.ui.composables.CookitTextField
import com.example.cookit.ui.theme.BackgroundColor
import com.example.cookit.ui.theme.CardBackground
import com.example.cookit.ui.theme.DisabledGray
import com.example.cookit.ui.theme.ErrorRed
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.ui.theme.White

@Composable
fun TitleStep(
    draft: RecipeDraft,
    onNext: () -> Unit
) {
    var title by remember { mutableStateOf(draft.title) }
    var titleError by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor)
            .padding(24.dp)
    ) {
        // Heading
        Text(
            text = "Recipe Title",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = PrimaryColor,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        CookitTextField(
            value = title,
            onValueChange = {
                title = it
                titleError = null
            },
            label = "Title",
            isError = titleError != null,
            errorMessage = titleError
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Next Button
        Button(
            onClick = {
                draft.title = title
                onNext()
            },
            enabled = title.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                contentColor = White,
                disabledContainerColor = DisabledGray,
                disabledContentColor = White
            ),
            modifier = Modifier
                .align(Alignment.End)
                .height(48.dp)
                .widthIn(min = 120.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text(text = "Next", style = MaterialTheme.typography.titleMedium)
        }
    }
}