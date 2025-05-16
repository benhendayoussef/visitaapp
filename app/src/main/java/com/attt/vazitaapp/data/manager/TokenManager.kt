package com.attt.vazitaapp.data.manager

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.navigation.NavController
import com.attt.vazitaapp.MainActivity
import org.json.JSONObject
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class TokenManager private constructor() {
    companion object {
        @Volatile
        private var instance: TokenManager? = null

        fun getInstance(): TokenManager {
            return instance ?: synchronized(this) {
                instance ?: TokenManager().also { instance = it }
            }
        }
    }

    private var navController: NavController?=null
    // Using nullable String for token
    private var token: String? = null

    fun setNavController(navController: NavController) {
        this.navController = navController
    }
    fun getNavController(): NavController? {
        return navController
    }

    // Method to save the token
    fun saveToken(newToken: String?) {
        token = newToken
    }

    // Method to get the current token
    fun getToken(): String? {
        return token
    }

    // Method to check if token is valid
    fun isTokenValid(): Boolean {
        // You can add more sophisticated token validation logic here
        return !token.isNullOrBlank()
    }

    // Logout method to clear the token
    fun logout() {
        // Clear the token

        token = null
        val activity:Activity?= MainActivity.getInstance()
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity?.startActivity(intent)
        activity?.finish()
        // TODO: Implement navigation logic to login screen
        // This might look different depending on your app's navigation framework
        // For example in Android:
        // val intent = Intent(context, LoginActivity::class.java)
        // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        // context.startActivity(intent)
    }

    // Optional: Check if user is authenticated
    fun isAuthenticated(): Boolean {
        return isTokenValid()
    }
}
