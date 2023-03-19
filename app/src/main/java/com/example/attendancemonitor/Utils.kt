package com.example.attendancemonitor

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import kotlin.math.ceil

var subjects = mutableStateListOf(
    Daata("DBMS" ,"Data Base Management System", 0 , 0 , 100f),
    Daata("JAVA" ,"JAVA", 0 , 0 , 100f),
    Daata("TOC" ,"Theory of Computation", 0 , 0 , 100f),
    Daata("PSLP" ,"Probability Statistics and Linear Programming", 0 , 0 , 100f),
    Daata("C & S" ,"Circuits and System" , 0 , 0 , 100f),
    Daata("TW" ,"Technical Writing", 0 , 0 , 100f),
)

var colorslist = listOf(
    Color(255, 0, 132, 255),
    Color(250, 76, 0, 255),
)

var RequiredPercentage = 75

var last : String = ""
var secondlast : String = ""

fun percentage(it: Daata): Float {
    if (it.present + it.absent == 0) return 0f
    val pres: Float = it.present.toFloat()
    val abs: Float = it.absent.toFloat()
    return (pres * 100 / (abs + pres))
}

fun preq(it : Daata) : Int{
    val pres = it.present
    val abs = it.absent
    if(it.percentage >= RequiredPercentage.toFloat()) return 0
//    return 3*it.absent - it.present
    return ceil((((abs+pres)*RequiredPercentage.toFloat()/100-pres)/((100-RequiredPercentage.toFloat())/100))).toInt()


}

