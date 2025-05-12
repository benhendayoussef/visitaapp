package com.attt.vazitaapp.data.model

import java.util.Objects

class State(
    val error:Boolean,
    val loading:Boolean=false,
    val message:String,
    val data: Any?
)