package com.example.cookit.ui.screens.auth.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookit.R
import com.example.cookit.data.network.AuthRepository
import com.example.cookit.data.network.RetrofitInstance
import com.example.cookit.data.utils.PrefManager
import com.example.cookit.ui.screens.auth.models.LoginRequest
import com.example.cookit.ui.screens.auth.viewModel.AuthViewModel
import com.example.cookit.ui.screens.auth.viewModel.AuthViewModelFactory
import com.example.cookit.ui.screens.model.AuthUiState

@Composable
fun LoginScreen(
    context: Context,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val repository = AuthRepository(RetrofitInstance.api)
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(repository))
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
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it.trim()
                nameError = null
            },
            label = { Text("Username or Email") },
            isError = nameError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (nameError != null) {
            Text(nameError!!, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it.trim()
                passwordError = null
            },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Toggle password"
                    )
                }
            },
            isError = passwordError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError != null) {
            Text(passwordError!!, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
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
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onNavigateToRegister) {
            Text("Don't have an account? Register")
        }
        if (uiState is AuthUiState.Error) {
            Text(
                text = uiState.message,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}