package com.attt.vazitaapp.data.repository

import android.content.Context
import android.util.Log
import com.attt.vazitaapp.data.manager.TokenManager
import com.attt.vazitaapp.data.model.User
import com.attt.vazitaapp.data.requestModel.GetUserInfoResponse
import com.attt.vazitaapp.data.requestModel.LogoutResponse
import com.attt.vazitaapp.data.source.remote.Services
import com.attt.vazitaapp.data.requestModel.SignInRequest
import com.attt.vazitaapp.data.requestModel.SignInResponse
import com.attt.vazitaapp.data.source.local.UserLocally
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.sign

class UserRepository (
    context: Context?= null
){


    private val userLocally = if(context!=null)UserLocally(context) else null


    suspend fun login(username:String,password: String,rememberMe:Boolean): SignInResponse {
        return withContext(Dispatchers.IO) {
            try {
                val call =
                    Services.getClientService().login(SignInRequest(username, password))
                val response = withContext(Dispatchers.IO) { call.execute() }
                val signinResponse = response.body()
                println("response code: ${response.code()}")
                println("body: " + signinResponse)
                if (signinResponse == null) {
                    SignInResponse(response.code(),"","","","", response.message(), null)

                } else {

                    Log.d("UserRepository", "Token:"+signinResponse.token)
                    if(signinResponse.token!=null){
                        TokenManager.getInstance().saveToken(signinResponse.token)
                        Log.d("UserRepository", "userLocally:"+userLocally)
                        if(rememberMe)
                        userLocally?.saveUser(
                            username = signinResponse.username ?: "",
                            center = signinResponse.id_centre ?: "",
                            role = signinResponse.role?:"",
                            token = signinResponse.token ?: ""
                        )
                    }
                    SignInResponse(
                        code = response.code(),
                        message = signinResponse.message ?: "",
                        token = signinResponse.token ?: "",
                        username = signinResponse.username ?: "",
                        id_centre = signinResponse.id_centre ?: "",
                        role = signinResponse.role?:"",
                        data = signinResponse.data ?: ""
                    )




                }
            } catch (e: Exception) {
                SignInResponse(500,"","","","", e.message.toString(), null)
            }

        }
    }


    suspend fun logout(): LogoutResponse {
        return withContext(Dispatchers.IO) {
            try {
                val call =
                    Services.getClientService().logout()
                val response = withContext(Dispatchers.IO) { call.execute() }
                val signinResponse = response.body()
                println("response code: ${response.code()}")
                println("body: " + signinResponse)
                if (signinResponse == null) {
                    LogoutResponse(response.code(),response.message(), null)

                } else {

                    if(response.isSuccessful){

                        userLocally?.clearUser()

                    }
                    LogoutResponse(
                        code = response.code(),
                        message = signinResponse.message ?: "",
                        data = signinResponse.data ?: ""
                    )





                }
            } catch (e: Exception) {
                LogoutResponse(500, e.message.toString(), null)
            }

        }
    }

    suspend fun isLoggedIn(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                userLocally?.isLoggedIn()?: false

            } catch (e: Exception) {
                false
            }
        }
    }
    suspend fun saveUserData(username: String, id_centre: String, role: String,token:String) {
        return withContext(Dispatchers.IO) {
            try {
                userLocally
                    ?.saveUser(
                        username = username,
                        center = id_centre,
                        role = role,
                        token = token
                    )
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun getUserData(): SignInResponse {
        return withContext(Dispatchers.IO) {
            try {
                SignInResponse(
                    code = 200,
                    message = "",
                    token = userLocally?.getToken(),
                    username = userLocally?.getUsername(),
                    id_centre = userLocally?.getCentre(),
                    role = userLocally?.getRole(),
                    data = null
                )
            } catch (e: Exception) {
                SignInResponse(
                    code = 200,
                    message = "",
                    token = "",
                    username = "",
                    id_centre = "",
                    role = "",
                    data = null
                )
            }
        }
    }

    suspend fun getUserInfo(): GetUserInfoResponse {
        return withContext(Dispatchers.IO) {
            try {

                val call =
                    Services.getClientService().getUserInfo()
                val response = withContext(Dispatchers.IO) { call.execute() }
                val signinResponse = response.body()
                println("response code: ${response.code()}")
                println("body: " + signinResponse)
                if (signinResponse == null) {
                    GetUserInfoResponse(
                        response.code(),
                        response.message(),
                        null

                    )

                } else {

                    Log.d("UserRepository", "Token:"+signinResponse.message)

                    GetUserInfoResponse(
                        code = response.code(),
                        message = signinResponse.message ?: "",
                        data = signinResponse.data
                    )




                }
            } catch (e: Exception) {
                GetUserInfoResponse(500, e.message.toString(), null)
            }

        }
    }


}