package com.attt.vazitaapp.data.requestModel

import com.attt.vazitaapp.data.model.User

data class GetUserInfoResponse(
    val code: Int,
    val message: String? ="",
    val data: User? =null
)