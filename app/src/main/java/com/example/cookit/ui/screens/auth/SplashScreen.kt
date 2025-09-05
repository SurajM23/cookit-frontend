package com.example.cookit.ui.screens.auth

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cookit.R
import com.example.cookit.data.utils.PrefManager
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    context: Context,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    // Display splash for 4 seconds
    LaunchedEffect(Unit) {
        delay(4000)
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(180.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}