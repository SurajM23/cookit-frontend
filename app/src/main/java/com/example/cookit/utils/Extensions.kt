package com.example.cookit.utils

import retrofit2.Response

/**
 * Extension function to safely extract data from Retrofit Response
 */
fun <T> Response<T>.toResource(): Resource<T> {
    return try {
        if (isSuccessful) {
            body()?.let {
                Resource.Success(it)
            } ?: Resource.Error("Empty response body")
        } else {
            val errorMsg = when (code()) {
                401 -> "Unauthorized - Please login again"
                403 -> "Forbidden - Access denied"
                404 -> "Not found"
                500 -> "Server error - Please try again later"
                else -> errorBody()?.string() ?: message() ?: "Unknown error"
            }
            Resource.Error(errorMsg)
        }
    } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Network error occurred")
    }
}

/**
 * Extension function to check if string is a valid email
 */
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Extension function to check if string meets password requirements
 */
fun String.isValidPassword(): Boolean {
    return length >= 6
}
