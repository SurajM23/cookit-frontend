package com.example.cookit.ui.screens.auth

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.cookit.R
import com.example.cookit.utils.PrefManager
import com.example.cookit.ui.composables.CookitActionButton
import com.example.cookit.ui.composables.CookitTextButton
import com.example.cookit.ui.composables.CookitTextField
import com.example.cookit.model.LoginRequest
import com.example.cookit.viewModel.AuthViewModel
import com.example.cookit.model.AuthUiState
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Eye
import compose.icons.fontawesomeicons.solid.EyeSlash

@Composable
fun LoginScreen(
    context: Context,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val uiState = viewModel.uiState

    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Success -> {
                val prefManager = PrefManager.getInstance(context)
                prefManager.saveToken(uiState.authResponse.token)
                prefManager.saveUserName(uiState.authResponse.user.name)
                prefManager.saveUserId(uiState.authResponse.user.id)
                prefManager.saveUserEmail(uiState.authResponse.user.email)
                onLoginSuccess()
            }

            is AuthUiState.Error -> {
                if (uiState.message.contains("name")) {
                    nameError = uiState.message
                } else {
                    passwordError = uiState.message
                }
            }

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App logo",
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))

        CookitTextField(
            value = username,
            onValueChange = {
                username = it
                nameError = null
            },
            label = "Username",
            isError = nameError != null,
            errorMessage = nameError
        )


        CookitTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null
            },
            label = "Password",
            isError = passwordError != null,
            errorMessage = passwordError,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) FontAwesomeIcons.Solid.Eye else FontAwesomeIcons.Solid.EyeSlash,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CookitActionButton(
            text = "Login",
            onClick = {
                var isValid = true
                if (username.isBlank()) {
                    nameError = "Username or email cannot be empty"
                    isValid = false
                }
                if (password.length < 6) {
                    passwordError = "Password must be at least 6 characters"
                    isValid = false
                }
                if (isValid) {
                    viewModel.loginUser(LoginRequest(username, password))
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        CookitTextButton(
            message = "Don't have an account? Register",
            onClick = onNavigateToRegister
        )

        if (uiState is AuthUiState.Error) {
            Text(
                text = uiState.message,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}