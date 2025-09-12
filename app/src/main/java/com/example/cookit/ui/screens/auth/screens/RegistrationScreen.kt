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
import com.example.cookit.ui.screens.auth.models.RegisterRequest
import com.example.cookit.data.network.AuthRepository
import com.example.cookit.data.network.RetrofitInstance
import com.example.cookit.data.utils.PrefManager
import com.example.cookit.ui.composables.CookitActionButton
import com.example.cookit.ui.composables.CookitTextButton
import com.example.cookit.ui.composables.CookitTextField
import com.example.cookit.ui.screens.auth.viewModel.AuthViewModel
import com.example.cookit.ui.screens.auth.viewModel.AuthViewModelFactory
import com.example.cookit.ui.screens.model.AuthUiState
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Eye
import compose.icons.fontawesomeicons.solid.EyeSlash

@Composable
fun RegistrationScreen(
    context: Context,
    onRegistrationSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AuthViewModel
) {

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Error states
    var nameError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
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
                onRegistrationSuccess
            }

            is AuthUiState.Error -> {

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

        // Name
        CookitTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = null
            },
            label = "Name",
            isError = nameError != null,
            errorMessage = nameError
        )

        // Username
        CookitTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = null
            },
            label = "Username",
            isError = usernameError != null,
            errorMessage = usernameError
        )

        CookitTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null
            },
            label = "Email",
            isError = emailError != null,
            errorMessage = emailError
        )

        // Password
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
            text = "Register",
            onClick = {
                var isValid = true
                if (name.isBlank()) {
                    nameError = "Name cannot be empty"
                    isValid = false
                }
                if (username.isBlank()) {
                    usernameError = "Username cannot be empty"
                    isValid = false
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailError = "Enter a valid email"
                    isValid = false
                }
                if (password.length < 6) {
                    passwordError = "Password must be at least 6 characters"
                    isValid = false
                }

                if (isValid) {
                    viewModel.registerUser(
                        RegisterRequest(name, username, email, password)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        CookitTextButton(
            message = "Back to Login",
            onClick =  onNavigateBack
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

