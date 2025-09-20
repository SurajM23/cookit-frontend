package com.example.cookit.ui.screens.addPost

// java/com/example/cookit/ui/screens/addPost/ImageStep.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.cookit.ui.theme.BackgroundColor
import com.example.cookit.ui.theme.CardBackground
import com.example.cookit.ui.theme.DisabledGray
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.ui.theme.TextSecondary
import com.example.cookit.ui.theme.White

@Composable
fun ImageStep(
    draft: RecipeDraft,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onPickImage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor)
            .padding(24.dp)
    ) {
        // Heading
        Text(
            text = "Add Image",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = PrimaryColor,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Image preview or placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CardBackground),
            contentAlignment = Alignment.Center
        ) {
            if (draft.imageUri != null) {
                AsyncImage(
                    model = draft.imageUri,
                    contentDescription = "Recipe Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = "No image selected",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pick Image Button
        Button(
            onClick = onPickImage,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                contentColor = White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Pick Image", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Navigation Buttons
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onBack,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = CardBackground,
                    contentColor = PrimaryColor,
                    disabledContentColor = DisabledGray
                ),
                modifier = Modifier
                    .height(48.dp)
                    .widthIn(min = 120.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) { Text("Back") }

            Button(
                onClick = onNext,
                enabled = draft.imageUri != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = White,
                    disabledContainerColor = DisabledGray,
                    disabledContentColor = White
                ),
                modifier = Modifier
                    .height(48.dp)
                    .widthIn(min = 120.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) { Text("Next") }
        }
    }
}
