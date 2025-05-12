package com.attt.vazitaapp.data.requestModel

data class LogoutResponse(
    val code: Int,
    val message: String? ="",
    val data: Any? =null
)