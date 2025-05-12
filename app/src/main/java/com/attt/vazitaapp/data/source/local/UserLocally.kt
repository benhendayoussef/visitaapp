package com.attt.vazitaapp.data.source.local


import android.content.Context
import android.content.SharedPreferences

class UserLocally(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_USERNAME = "username"
        private const val KEY_ROLE = "role"
        private const val KEY_CENTRE = "centre"
    }

    fun saveUser(token: String, username: String, role: String,center: String) {
        sharedPreferences.edit().apply {
            putString(KEY_TOKEN, token)
            putString(KEY_USERNAME, username)
            putString(KEY_ROLE, role)
            putString(KEY_CENTRE, center)
            apply()
        }
    }

    fun getToken(): String? = sharedPreferences.getString(KEY_TOKEN, null)

    fun getUsername(): String? = sharedPreferences.getString(KEY_USERNAME, null)

    fun getRole(): String? = sharedPreferences.getString(KEY_ROLE, null)

    fun getCentre(): String? = sharedPreferences.getString(KEY_CENTRE, null)

    fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = getToken() != null
}
