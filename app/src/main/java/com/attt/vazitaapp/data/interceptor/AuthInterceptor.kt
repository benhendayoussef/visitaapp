import android.util.Log
import com.attt.vazitaapp.data.manager.TokenManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("AuthInterceptor", "intercept called")
        val request = chain.request()
        Log.d("AuthInterceptor", "Request URL: ${request.url}")

        // Attach access token to the request
        val token = tokenManager.getToken()
        if (token == null) {
            Log.d("AuthInterceptor", "Access token is null")
        }

        val authRequest = if (isLoginRequest(request)) {
            request

        } else {
            request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        }
        Log.d("AuthInterceptor", "Request with Authorization header: ${authRequest.headers}")


        var response = chain.proceed(authRequest)
        Log.d("AuthInterceptor", "Response code: ${response.code}")

        // If access token expired
        if (response.code == 403) {
            Log.d("AuthInterceptor", "Access token expired, refreshing token")
            response.close() // Close the first response
            // Retry the request with a new access token
            // Synchronize to prevent multiple threads from refreshing the token at the same time
            Log.d("AuthInterceptor", "Synchronizing on token refresh")
            tokenManager.logout()
        }

        return response
    }
    private fun isLoginRequest(request: Request): Boolean {
        val url = request.url.toString()
        return url.contains("/auth/login")
    }
}
