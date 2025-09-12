package com.example.cookit.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp

@Composable
fun CookitTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: (@Composable (() -> Unit))? = null,
    textColor: Color = Color.Black,
    cursorColor: Color = Color.Black,
    focusedBorderColor: Color = Color.Red,
    unfocusedBorderColor: Color = Color.Black,
    errorBorderColor: Color = Color.Red,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = textColor) },
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            trailingIcon = trailingIcon,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = focusedBorderColor,
                unfocusedBorderColor = unfocusedBorderColor,
                errorBorderColor = errorBorderColor,
                cursorColor = cursorColor,
                focusedLabelColor = focusedBorderColor,
                unfocusedLabelColor = textColor,
                errorLabelColor = errorBorderColor,
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                errorTextColor = errorBorderColor
            )
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = errorBorderColor,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
        }
    }
}