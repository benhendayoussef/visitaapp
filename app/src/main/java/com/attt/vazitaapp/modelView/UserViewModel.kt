package com.attt.vazitaapp.modelView

import android.content.Context
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.attt.vazitaapp.data.manager.TokenManager
import com.attt.vazitaapp.data.model.User
import com.attt.vazitaapp.data.repository.UserRepository
import com.attt.vazitaapp.data.requestModel.LogoutResponse
import com.attt.vazitaapp.data.requestModel.SignInResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel@Inject constructor(

) :ViewModel() {



    private var userRepository: UserRepository? = null

    fun setRepository(userRepository: UserRepository) {
        this.userRepository = userRepository
    }


    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName
    private val _role= MutableLiveData<String>()
    val role: LiveData<String> get() = _role
    private val _centerId = MutableLiveData<String>()
    val centerId: LiveData<String> get() = _centerId

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun setUser(user: User) {
        _user.value = user
    }
    fun getUser(): User? {
        return _user.value
    }

    fun setUserName(name: String) {
        _userName.value = name
    }
    fun setRole(role: String) {
        _role.value = role
    }
    fun setCenterId(centerId: String) {
        _centerId.value = centerId
    }
    fun getUserName(): String? {
        return _userName.value
    }
    fun getRole(): String? {
        return _role.value
    }

    suspend fun logout(
        any: (LogoutResponse) -> Unit
    ) {
        val response = userRepository?.logout()
        if(response != null) {
            any(response)

        }


    }

    suspend fun loadUserLocally() {
        val user = userRepository?.getUserData()
        setRole(user?.role?:"")
        setUserName(user?.username?:"")
        setCenterId(user?.id_centre?:"")
        TokenManager.getInstance().saveToken(user?.token?:"")




    }

    suspend fun getUserInfo(){
        val user = userRepository?.getUserInfo()
        if(user!=null && user.data!=null){

            setUser(user.data)
            setRole(user.data.designation)
            setUserName(user.data.username)
            setCenterId(user.data.idCentre.toString())
        }
    }

}