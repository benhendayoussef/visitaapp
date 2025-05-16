package com.attt.vazitaapp.data.model

data class PointDefault(
    val codePoint:Int,
    val libellePoint:String,
    val codeChapitre: Int,
    val alterationResponses: List<Alteration>,
    val isViewed: Boolean = false,

    )