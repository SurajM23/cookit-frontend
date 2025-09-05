package com.example.cookit.ui.screens.auth

import android.app.ProgressDialog.show
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookit.R
import com.example.cookit.data.network.AuthRepository
import com.example.cookit.data.network.RetrofitInstance
import com.example.cookit.data.utils.PrefManager
import com.example.cookit.ui.screens.auth.models.LoginRequest
import com.example.cookit.ui.screens.auth.viewModel.AuthViewModel
import com.example.cookit.ui.screens.auth.viewModel.AuthViewModelFactory
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    context: Context,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val repository = AuthRepository(RetrofitInstance.api)
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(repository))
    viewModel.loginUser(LoginRequest("username", "password"))

    // Animation state for the app name
    var show by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (show) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alphaAnimation"
    )

    val scale by animateFloatAsState(
        targetValue = if (show) 1f else 0.8f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "scaleAnimation"
    )

    // Splash screen delay and logic
    LaunchedEffect(Unit) {
        delay(1000) // Short delay before showing the app name
        show = true

        delay(3000) // Wait 3 more seconds (total 4s)
        val prefs = PrefManager.getInstance(context)
        val token = prefs.getToken()
        val userId = prefs.getUserId()
        val email = prefs.getUserEmail()
        val username = prefs.getUserName()

        if (!token.isNullOrEmpty() && !userId.isNullOrEmpty() && !email.isNullOrEmpty() && !username.isNullOrEmpty()) {
            onNavigateToHome()
        } else {
            onNavigateToLogin()
        }
    }

    // UI
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(280.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Animated App Name
            Text(
                text = "CookIT",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.6f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                ),
//                color = MaterialTheme.colorScheme.primary,
                color = Color.Red,
                modifier = Modifier
                    .graphicsLayer {
                        this.alpha = alpha
                        this.scaleX = scale
                        this.scaleY = scale
                    }
            )
        }
    }
}
