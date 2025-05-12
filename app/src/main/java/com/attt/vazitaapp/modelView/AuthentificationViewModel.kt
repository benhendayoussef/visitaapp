package com.attt.vazitaapp.modelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.attt.vazitaapp.data.requestModel.SignInResponse
import com.attt.vazitaapp.data.repository.UserRepository
import com.attt.vazitaapp.data.model.State
import javax.inject.Inject

class AuthentificationViewModel@Inject constructor(

) :ViewModel() {

    private var userRepository: UserRepository?=null
    fun setUserRepository(userRepository: UserRepository) {
        this.userRepository = userRepository
    }


    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    private val _rememberMe = MutableLiveData<Boolean>()
    val rememberMe: LiveData<Boolean> get() = _rememberMe

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _password2 = MutableLiveData<String>()
    val password2: LiveData<String> get() = _password2


    private val _code = MutableLiveData<String>()
    val code: LiveData<String> get() = _code

    fun onStateChange(newState: State) {
        _state.value = newState
    }

    fun onRememberMeChange(newRememberMe: Boolean) {
        _rememberMe.value = newRememberMe
    }

    fun onUserNameChange(newEmail: String) {
        _userName.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onPassword2Change(newPassword2: String) {
        _password2.value = newPassword2
    }

    fun onCodeChange(newCode: String) {
        _code.value = newCode
    }

    fun validateCredentials(): Boolean {
        return !(_userName.value.isNullOrEmpty() || _password.value.isNullOrEmpty() || _password.value != _password2.value)
    }

    suspend fun signIn(
        userName: String,
        password: String,
        rememberMe: Boolean,
        any: (SignInResponse) -> Unit
    ) {
        val response = userRepository?.login(userName, password, rememberMe)
        if(response!=null)
            any(response)

    }

}