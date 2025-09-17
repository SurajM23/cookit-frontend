package com.example.cookit.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object PrefManager {

    private const val PREF_NAME = "cookit_prefs"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_EMAIL = "user_email"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) = prefs.edit { putString(KEY_TOKEN, token) }
    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun saveUserName(name: String) = prefs.edit { putString(KEY_USER_NAME, name) }
    fun getUserName(): String? = prefs.getString(KEY_USER_NAME, null)

    fun saveUserId(id: String) = prefs.edit { putString(KEY_USER_ID, id) }
    fun getUserId(): String? = prefs.getString(KEY_USER_ID, null)

    fun saveUserEmail(email: String) = prefs.edit { putString(KEY_USER_EMAIL, email) }
    fun getUserEmail(): String? = prefs.getString(KEY_USER_EMAIL, null)

    fun clearAll() = prefs.edit { clear() }
}
