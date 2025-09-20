package com.example.cookit.ui.screens.addPost

// java/com/example/cookit/ui/screens/addPost/StepsStep.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import com.example.cookit.ui.theme.TextPrimary
import com.example.cookit.ui.theme.White

@Composable
fun StepsStep(
    draft: RecipeDraft,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var steps by remember { mutableStateOf(draft.steps.toMutableList()) }
    var newStep by remember { mutableStateOf("") }
    var stepError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor)
            .padding(24.dp)
    ) {
        // Heading
        Text(
            text = "Recipe Steps",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = PrimaryColor,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(16.dp))

        // Existing Steps List
        steps.forEachIndexed { idx, step ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        "${idx + 1}. $step",
                        color = TextPrimary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(onClick = { steps.removeAt(idx) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove", tint = ErrorRed)
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // New Step Input
        CookitTextField(
            value = newStep,
            onValueChange = {
                newStep = it
                stepError = null
            },
            label = "Add new step",
            isError = stepError != null,
            errorMessage = stepError
        )

        Button(
            onClick = {
                if (newStep.isNotBlank()) {
                    steps.add(newStep)
                    newStep = ""
                } else {
                    stepError = "Step cannot be empty"
                }
            },
            enabled = newStep.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                contentColor = White,
                disabledContainerColor = DisabledGray,
                disabledContentColor = White
            ),
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
                .height(48.dp)
                .widthIn(min = 120.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text("Add", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.height(24.dp))

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
                    draft.steps = steps
                    onNext()
                },
                enabled = steps.isNotEmpty(),
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
