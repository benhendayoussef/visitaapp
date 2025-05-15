package com.attt.vazitaapp.data.model

data class PointDefault(
    val CODE_POINT:Int,
    val LIBELLE_POINT:String,
    val CODE_CHAPITRE: Int,
    val alterations: List<Alteration>,
    val isViewed: Boolean = false,

)