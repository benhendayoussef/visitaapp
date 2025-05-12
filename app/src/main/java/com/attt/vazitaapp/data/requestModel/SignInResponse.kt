package com.attt.vazitaapp.data.requestModel

data class SignInResponse(
    val code: Int,
    val token: String?,
    val username: String?,
    val id_centre: String?,
    val role: String?,
    val message: String? ="",
    val data: Any? =null
)