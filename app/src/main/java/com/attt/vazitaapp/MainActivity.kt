package com.attt.vazitaapp

import AuthInterceptor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.attt.vazitaapp.data.manager.TokenManager
import com.attt.vazitaapp.data.repository.UserRepository
import com.attt.vazitaapp.data.source.remote.Services
import com.attt.vazitaapp.data.source.remote.UserService
import com.attt.vazitaapp.modelView.AuthentificationViewModel
import com.attt.vazitaapp.modelView.DossierViewModel
import com.attt.vazitaapp.modelView.UserViewModel
import com.attt.vazitaapp.ui.theme.VazitaappTheme
import com.attt.vazitaapp.view.Login
import com.attt.vazitaapp.view.MainApp
import com.attt.vazitaapp.view.SplashScreen
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log request and response bodies
        }

        setContent {
            VazitaappTheme {
                val navController = rememberNavController() // Better naming
                val tokenManager = TokenManager.getInstance()
                tokenManager.setNavController(navController)
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(AuthInterceptor(tokenManager))
                    .build()

                val Url = "https://dream.serveo.net/"
                val retrofit = Retrofit.Builder()
                    .baseUrl(Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                val userService: UserService = retrofit.create(UserService::class.java)
                Services.setClientService(userService)

                val animationSpeed = 1000
                val duration = 1000
                val authViewModel: AuthentificationViewModel = viewModel()
                val userViewModel: UserViewModel = viewModel()
                val dossierViewModel: DossierViewModel = viewModel()
                val context = LocalContext.current
                val repository=UserRepository(context)
                userViewModel.setRepository(repository)
                authViewModel.setUserRepository(repository)
                NavHost(
                    navController = navController,
                    startDestination = "MainApp",
                ) {
                    composable(
                        route = "SplashScreen",
                        exitTransition = {
                            fadeOut(animationSpec = tween(300)) +
                                    scaleOut(targetScale = 1.5f, animationSpec = tween(500))
                        }
                    ) {
                        SplashScreen(navController=navController,userViewModel=userViewModel)
                    }


                    composable(
                        route = "Login",
                        enterTransition = {
                            fadeIn(animationSpec = tween(duration))
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(duration))
                        }
                    ) {
                        Login(navController, authViewModel = authViewModel,userViewModel=userViewModel)
                    }
                    composable(
                            route = "MainApp",
                    enterTransition = {
                        fadeIn(animationSpec = tween(duration))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(duration))
                    }
                    ) {
                        MainApp(navController,userViewModel=userViewModel,dossierViewModel=dossierViewModel)
                }
                }
            }
        }
    }
}
