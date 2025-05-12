package com.attt.vazitaapp.view

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.attt.vazitaapp.R
import com.attt.vazitaapp.modelView.AuthentificationViewModel
import com.attt.vazitaapp.modelView.UserViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.attt.vazitaapp.view.page.SelectionDePiste


@Composable
fun MainApp(

    navController: NavController,
    userViewModel: UserViewModel
) {

    val coroutineScope = rememberCoroutineScope()
    val userName by userViewModel.userName.observeAsState("")
    val role by userViewModel.role.observeAsState("")
    val centerId by userViewModel.centerId.observeAsState("")

    val animationSpeed = 1000
    val duration = 1000
    val navController = rememberNavController() // Better naming

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),

                )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = "Welcome $userName",
                        fontSize = 36.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Text(
                        text = "Role: $role",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Text(
                        text = "Center ID: $centerId",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    Image(
                        painter = painterResource(id = R.drawable.logout_profile),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier
                            .size(60.dp)
                            .padding(8.dp)
                            .clickable {
                                coroutineScope.launch {
                                    try {
                                        userViewModel.logout({
                                            print(it.code)
                                            when (it.code) {
                                                200, 201 -> {
                                                    navController.navigate("login")
                                                }

                                                else -> {
                                                    Toast.makeText(
                                                        navController.context,
                                                        it.message,
                                                        Toast.LENGTH_LONG
                                                    ).show()

                                                }

                                            }
                                            when (it.code) {
                                                200, 201 -> {
                                                    navController.navigate("login")
                                                }

                                                else -> {
                                                    Toast.makeText(
                                                        navController.context,
                                                        it.message,
                                                        Toast.LENGTH_LONG
                                                    ).show()

                                                }

                                            }
                                        })

                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }

                    )
                }
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "SelectionPiste",
                ) {
                    composable(
                        route = "SelectionPiste",
                        exitTransition = {
                            fadeOut(animationSpec = tween(300)) +
                                    scaleOut(targetScale = 1.5f, animationSpec = tween(500))
                        }
                    ) {
                        SelectionDePiste(navController=navController)
                    }

                }

            }
        }
    )

}