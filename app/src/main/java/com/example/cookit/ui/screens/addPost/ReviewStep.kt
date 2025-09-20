package com.example.cookit.ui.screens.addPost

// java/com/example/cookit/ui/screens/addPost/ReviewStep.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cookit.model.ApiResult
import com.example.cookit.model.CreateRecipeRequest
import com.example.cookit.model.SimpleMessageResponse
import com.example.cookit.viewModel.HomeViewModel
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.ui.theme.SecondaryColor
import com.example.cookit.ui.theme.BackgroundColor
import com.example.cookit.ui.theme.TextPrimary
import com.example.cookit.ui.theme.TextSecondary
import com.example.cookit.ui.theme.CardBackground
import com.example.cookit.ui.theme.DividerColor
import com.example.cookit.ui.theme.AccentGreen
import com.example.cookit.ui.theme.ErrorRed
import kotlinx.coroutines.delay


@Composable
fun ReviewStep(
    homeViewModel: HomeViewModel,
    draft: RecipeDraft,
    onBack: () -> Unit,
    onSuccessNavigate: () -> Unit
) {
    var submitted by remember { mutableStateOf(false) }
    val createRecipeState by homeViewModel.createRecipeResponse.collectAsState()

    when {
        submitted && createRecipeState is ApiResult.Loading -> {
            // Full screen loading
            Box(
                Modifier
                    .fillMaxSize()
                    .background(BackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryColor)
            }
        }

        submitted && createRecipeState is ApiResult.Success -> {
            // Full screen success
            val response = (createRecipeState as ApiResult.Success<SimpleMessageResponse>).data
            LaunchedEffect(createRecipeState) {
                delay(2000)
                onSuccessNavigate()
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(BackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = AccentGreen,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = response.message,
                            style = MaterialTheme.typography.headlineSmall,
                            color = PrimaryColor,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Redirecting to Home...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        submitted && createRecipeState is ApiResult.Error -> {
            // Full screen error
            val error = (createRecipeState as ApiResult.Error).message
            Box(
                Modifier
                    .fillMaxSize()
                    .background(BackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = ErrorRed,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = error,
                            color = ErrorRed,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = {
                                submitted = false
                                onBack()
                            },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = SecondaryColor)
                        ) {
                            Text("Try Again")
                        }
                    }
                }
            }
        }

        else -> {
            // Default review screen
            Box(
                Modifier
                    .fillMaxSize()
                    .background(BackgroundColor)
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.align(Alignment.Center),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(Modifier.padding(24.dp)) {
                        Text(
                            "Review Recipe",
                            style = MaterialTheme.typography.titleLarge,
                            color = PrimaryColor
                        )
                        Spacer(Modifier.height(16.dp))
                        Divider(color = DividerColor)
                        Spacer(Modifier.height(12.dp))
                        AsyncImage(
                            model = draft.imageUri,
                            contentDescription = draft.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "Title",
                            color = TextSecondary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            draft.title,
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Ingredients",
                            color = TextSecondary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            draft.ingredients.joinToString(),
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Steps",
                            color = TextSecondary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            draft.steps.joinToString(),
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Cook Time",
                            color = TextSecondary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            "${draft.cookTime} ${draft.cookTimeUnit}",
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(20.dp))
                        Divider(color = DividerColor)
                        Spacer(Modifier.height(20.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(
                                onClick = onBack,
                                enabled = !submitted || createRecipeState is ApiResult.Error,
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = SecondaryColor)
                            ) { Text("Back") }
                            Button(
                                onClick = {
                                    submitted = true
                                    homeViewModel.createRecipePost(draft.toCreateRecipeRequest())
                                },
                                enabled = !submitted || createRecipeState is ApiResult.Error,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryColor,
                                    contentColor = Color.White
                                )
                            ) { Text("Submit") }
                        }
                    }
                }
            }
        }
    }
}
