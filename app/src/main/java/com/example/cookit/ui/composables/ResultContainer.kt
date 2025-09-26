package com.example.cookit.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookit.model.ApiResult
import com.example.cookit.ui.theme.PrimaryColor

@Composable
fun <T> ResultContainer(
    result: ApiResult<T>,
    onRetry: (() -> Unit)? = null,
    onUnauthorized: (() -> Unit)? = null,
    loading: @Composable (() -> Unit)? = null,
    success: @Composable (T) -> Unit
) {
    when (result) {
        is ApiResult.Loading -> {
            if (loading != null) {
                loading()
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryColor)
                }
            }
        }
        is ApiResult.Error -> {
            ErrorBox(
                message = result.message,
                onUnauthorized = { onUnauthorized?.invoke() },
                onRetry = onRetry
            )
        }
        is ApiResult.Success -> {
            success(result.data)
        }
    }
}

