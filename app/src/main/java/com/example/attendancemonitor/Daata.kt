package com.example.attendancemonitor

import androidx.compose.runtime.mutableStateListOf

data class Daata(
    var subject : String,
    var fullname : String,
    var absent : Int ,
    var present : Int ,
    var percentage : Float
)

