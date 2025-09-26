package com.example.cookit.data.network

import com.example.cookit.model.ApiResult
import retrofit2.Response

inline fun <reified T> Response<T>.toApiResult(): ApiResult<T> {
    return try {
        if (isSuccessful && body() != null) {
            ApiResult.Success(body()!!)
        } else if (code() == 401) {
            ApiResult.Error("Unauthorized")
        } else {
            ApiResult.Error(message().ifBlank { "Unknown error" })
        }
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Network error")
    }
}

