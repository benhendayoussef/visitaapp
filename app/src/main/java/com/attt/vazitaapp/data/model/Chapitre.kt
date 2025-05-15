package com.attt.vazitaapp.data.model

data class Chapitre(
    val CODE_CHAPITRE:Int,
    val LIBELLE_CHAPITRE:String,
    val isViewed: Boolean = false,
    val pointsDefault: List<PointDefault>,
)