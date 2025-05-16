package com.attt.vazitaapp.data.model

data class User(
    val idUser: String,
    val firstName: String,
    val lastName: String,
    val firstNameA: String,
    val lastNameA: String,
    val startDate: String,
    val endDate: String,
    val status: Boolean,
    val designation: String,
    val idCentre: Int
)