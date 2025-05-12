package com.attt.vazitaapp.view


import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.attt.vazitaapp.R
import com.attt.vazitaapp.data.repository.UserRepository
import com.attt.vazitaapp.modelView.UserViewModel
import com.attt.visitaapp.view.component.LoadingAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SplashScreen(
    userViewModel: UserViewModel,
    navController: NavController
) {
    // Define the missing variables
    val durationMillis = 2000 // 2 seconds for splash screen
    val scale = remember { Animatable(1f) }
    val offsetY = remember { Animatable(0f) }
    val alpha = 1f // Full opacity
    val context=LocalContext.current

    LaunchedEffect(Unit) {
        delay(durationMillis.toLong())
        launch {
            delay(1000)
            // Prepare animation before navigating
            scale.animateTo(1.2f, animationSpec = tween(300))
            offsetY.animateTo(-100f, animationSpec = tween(300))
            val userRepository=UserRepository(context)
            // Check if user is logged in
            Log.d("LocallySaved", userRepository.isLoggedIn().toString())
            val user=userViewModel.getUserName()


            if(userRepository.isLoggedIn()){

                userViewModel.loadUserLocally()
                navController.navigate("MainApp")

            }
            else{

                navController.navigate("Login") {
                    popUpTo("SplashScreen") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        // Modify your Logo call
        Logo(
            modifier = Modifier
                .graphicsLayer(
                    alpha = alpha,
                    scaleX = scale.value,
                    scaleY = scale.value,
                    translationY = offsetY.value
                )
        )
    }
}

@Composable
fun Logo(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_white),
            contentDescription = "logo image",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.size(50.dp))
        Text(
            text = context.getString(R.string.app_name),
            color = Color.White,
            fontSize = 50.sp,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Agence Technique des Transports Terrestres",
            color = Color.White,
            fontSize = 25.sp,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.size(50.dp))
        LoadingAnimation()
    }
}



