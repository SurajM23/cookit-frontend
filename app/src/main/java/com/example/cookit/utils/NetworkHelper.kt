package com.example.cookit.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Helper function to safely make API calls with proper error handling
 */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            response.toResource()
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}
