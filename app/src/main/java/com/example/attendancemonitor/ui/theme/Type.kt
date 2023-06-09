package com.example.attendancemonitor.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.attendancemonitor.R


val best = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_extrabold),
    Font(R.font.montserrat_bold),
    Font(R.font.montserrat_blackitalic),
    Font(R.font.montserrat_bolditalic),
    Font(R.font.montserrat_extrabolditalic),
    Font(R.font.montserrat_extralight),
    Font(R.font.montserrat_extralightitalic),
    Font(R.font.montserrat_lack),
)
// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = best,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)