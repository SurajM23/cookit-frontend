package com.example.cookit.data.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {
    companion object {
        private const val PREF_NAME = "cookit_prefs"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_ID = "user_name"
        private const val KEY_USER_EMAIL = "user_name"

        @Volatile
        private var INSTANCE: PrefManager? = null

        fun getInstance(context: Context): PrefManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PrefManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Token
    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    // User Name
    fun saveUserName(name: String) {
        prefs.edit().putString(KEY_USER_NAME, name).apply()
    }

    fun saveUserId(name: String) {
        prefs.edit().putString(KEY_USER_ID, name).apply()
    }

    fun saveUserEmail(name: String) {
        prefs.edit().putString(KEY_USER_EMAIL, name).apply()
    }

    fun getUserName(): String? = prefs.getString(KEY_USER_NAME, null)

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}