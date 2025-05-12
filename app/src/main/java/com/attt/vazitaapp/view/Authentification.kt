package com.attt.vazitaapp.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.attt.vazitaapp.R
import com.attt.vazitaapp.data.manager.TokenManager
import com.attt.vazitaapp.data.model.State
import com.attt.vazitaapp.modelView.AuthentificationViewModel
import com.attt.vazitaapp.modelView.UserViewModel
import com.attt.visitaapp.view.component.CustomButton
import com.attt.visitaapp.view.component.CustomTextField
import com.attt.visitaapp.view.component.PasswordTextField
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun Login(
    navController: NavController,
    authViewModel: AuthentificationViewModel,
    userViewModel: UserViewModel
) {
    val userName by authViewModel.userName.observeAsState("")
    val password by authViewModel.password.observeAsState("")
    val rememberMe by authViewModel.rememberMe.observeAsState(false)
    val state by authViewModel.state.observeAsState(State(false,false, "", Any()))
    LaunchedEffect(Unit) {
        authViewModel.onStateChange(State(false,false, "", Any()))
        authViewModel.onUserNameChange("")
        authViewModel.onPasswordChange("")
        authViewModel.onRememberMeChange(false)
    }

    val coroutineScope = rememberCoroutineScope()


    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()

    )
    {
        Column(
            modifier = Modifier.fillMaxSize()

        ){
            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(MaterialTheme.colorScheme.primary)


            ){

            }
            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary)


            ){

            }


        }
        Column(
            modifier = Modifier.fillMaxSize()

        ){
            Row(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(.3f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .fillMaxHeight(0.4f),
                    painter = painterResource(id = R.drawable.logo_white),
                    contentDescription = "logo image",
                )



            }
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 80.dp),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface,
                )


            ){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    Text(
                        text = "Authentifier",
                        fontSize = 50.sp,
                        style = MaterialTheme.typography.titleLarge
                    )
                        CustomTextField(
                            value = userName,
                            onValueChange = authViewModel::onUserNameChange,
                            placeholkder = "please enter your email",
                            label = "E-mail",
                            icon = painterResource(id = R.drawable.username),
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
                                .fillMaxWidth(0.7f),
                        )
                        PasswordTextField(
                            value = password,
                            onValueChange = authViewModel::onPasswordChange,
                            placeholkder = "please enter your password",
                            label = "Mot de passe",
                            icon = painterResource(id = R.drawable.password),
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                                .fillMaxWidth(0.7f)
                        )
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                                .fillMaxWidth(0.7f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = authViewModel::onRememberMeChange
                            )
                            Text(
                                text = "Se souvenir de moi",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.clickable {
                                    authViewModel.onRememberMeChange(!rememberMe)
                                }
                            )

                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            text = state.message,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            color = if(state.error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary
                        )
                    CustomButton(
                        text = "Connecter",
                        modifier = Modifier.fillMaxWidth(0.5f)
                            .height(100.dp)
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        color = MaterialTheme.colorScheme.primary,
                        enabled = !state.loading

                    )
                    {
                        //navController.navigate("MainApp")

                        println("envoie...")
                        if(userName.isEmpty() || password.isEmpty()){
                            authViewModel.onStateChange(State(true,false,"Verify your credentials Format",Any()))

                        }




                        coroutineScope.launch {
                            try {
                                authViewModel.onStateChange(State(true,true,"",Any()))
                                authViewModel.signIn(userName=userName, password=password,rememberMe=rememberMe){
                                    print("code recu: ${it.code}")
                                    //TODO compelte login code -> message
                                    when (it.code) {
                                        200,201 -> {
                                            authViewModel.onStateChange(State(false,false,"Login successfully done",it))
                                            println(it.message)
                                            userViewModel.setUserName(it.username?:"")
                                            Log.d("user",userViewModel.getUserName().toString())
                                            userViewModel.setRole(it.role?:"")

                                            if(it.token!=null) {
                                                TokenManager.getInstance().saveToken(it.token)


                                            }


                                            if(rememberMe){
                                                //userViewModel.saveStudentLocally(context)
                                            }


                                            navController.navigate("MainApp")
                                            //navController.navigate("CodeVerification")
                                        }
                                        429 ->{
                                            authViewModel.onStateChange(State(true,false,"429",it))
                                            println(it.message)
                                            //navController.navigate("CodeVerification")
                                        }
                                        404 ->{
                                            authViewModel.onStateChange(State(true,false,"404",it))
                                            println(it.message)
                                        }
                                        500 ->{
                                            authViewModel.onStateChange(State(true,false,"500",it))
                                            println(it.message)
                                        }
                                        401->{
                                            authViewModel.onStateChange(State(true,false,"401",it))
                                            println(it.message)
                                        }
                                        400->{
                                            authViewModel.onStateChange(State(true,false,"400",it))
                                            println(it.message)
                                        }
                                        409->{
                                            authViewModel.onStateChange(State(true,false,"409",it))
                                            println(it.message)
                                        }
                                        406->{
                                            authViewModel.onStateChange(State(true,false,"406",it))
                                            println(it.message)
                                        }
                                        451->{
                                            authViewModel.onStateChange(State(true,false,"you are Banned Hehe",it))
                                            println(it.message)

                                        }
                                        else -> {
                                            authViewModel.onStateChange(State(true,false,
                                                it.message?:"",it))
                                            println(it.message)
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                println(e.message)
                                authViewModel.onStateChange(State(true,false,"Login failed: ${e.message}",Any()))
                            }
                        }

                }

                }

            }
        }

    }
}




