package com.example.cookit.ui.screens.addPost

// java/com/example/cookit/ui/screens/addPost/CookTimeStep.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.example.cookit.ui.composables.CookitTextField
import com.example.cookit.ui.theme.BackgroundColor
import com.example.cookit.ui.theme.CardBackground
import com.example.cookit.ui.theme.DisabledGray
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.ui.theme.TextPrimary
import com.example.cookit.ui.theme.White

@Composable
fun CookTimeStep(
    draft: RecipeDraft,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var cookTime by remember { mutableStateOf(draft.cookTime) }
    var unit by remember { mutableStateOf(draft.cookTimeUnit) }
    val units = listOf("min", "hr")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor)
            .padding(24.dp)
    ) {
        // Heading
        Text(
            text = "Cook Time",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = PrimaryColor,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Cook Time Input
        CookitTextField(
            value = cookTime,
            onValueChange = { cookTime = it.filter { c -> c.isDigit() } },
            label = "Enter time",
            isError = cookTime.isBlank(),
            errorMessage = if (cookTime.isBlank()) "Cook time cannot be empty" else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Unit selection buttons
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            units.forEach { u ->
                OutlinedButton(
                    onClick = { unit = u },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (unit == u) PrimaryColor.copy(alpha = 0.2f) else CardBackground,
                        contentColor = if (unit == u) PrimaryColor else TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(44.dp)
                ) { Text(u, style = MaterialTheme.typography.bodyMedium) }
            }
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
                onClick = {
                    draft.cookTime = cookTime
                    draft.cookTimeUnit = unit
                    onNext()
                },
                enabled = cookTime.isNotBlank(),
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
