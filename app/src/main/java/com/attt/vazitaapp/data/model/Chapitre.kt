package com.attt.vazitaapp.data.model

data class Chapitre(
    val codeChapitre:Int,
    val libelleChapitre:String,
    val isViewed: Boolean = false,
    val pointDefautResponses: List<PointDefault>,
)