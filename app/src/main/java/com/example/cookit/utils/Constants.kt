package com.example.cookit.utils

object Constants {
    // API Configuration
    const val BASE_URL = "https://cookit-backend-gj6e.onrender.com/api/"
    
    // Network Configuration
    const val CONNECT_TIMEOUT = 30L // seconds
    const val READ_TIMEOUT = 30L // seconds
    const val WRITE_TIMEOUT = 30L // seconds
    
    // Pagination
    const val PAGE_SIZE = 20
    const val INITIAL_PAGE = 1
    
    // Validation
    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_USERNAME_LENGTH = 50
    
    // SharedPreferences Keys (handled by PrefManager)
    // See PrefManager.kt for actual usage
}