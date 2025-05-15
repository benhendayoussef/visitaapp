package com.attt.vazitaapp.data.model

data class Alteration(
    val CODE_ALTERATION:Int,
    val LIBELLE_ALTERATION:String,
    val CODE_CHAPITRE:Int,
    val CODE_POINT:Int,
    val isSelected:Boolean = false,

)