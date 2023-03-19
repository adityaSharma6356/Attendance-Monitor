package com.example.attendancemonitor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun Singleblock(currentsubject: Daata, height : Dp) {
    val context = LocalContext.current
    var expandedstate by remember { mutableStateOf(80) }
    val size by animateDpAsState(targetValue = expandedstate.dp)
    var pere by remember { mutableStateOf(currentsubject.present) }
    var abse by remember { mutableStateOf(currentsubject.absent) }
    currentsubject.present = pere
    currentsubject.absent = abse
    if(currentsubject.subject!=last){
        expandedstate = 80
    }
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .height(size),
        backgroundColor = Color(255, 255, 255, 255),
        elevation = 8.dp
    ) {
        ConstraintLayout {
            val (subjectname ,
                subjectfullname,
                presentcount ,
                absentcount ,
                percentage ,
                absentbuttonpos ,
                presentbuttonpos ,
                absentbuttonneg ,
                presentbuttonneg,
                absenttext,
                presenttext,
                remainingpresents
            ) = createRefs()
            val colorA : Color = if(currentsubject.percentage>= RequiredPercentage)
                Color(64, 255, 225, 255)
            else if (currentsubject.percentage>= RequiredPercentage-20)
                Color(255, 153, 103, 255)
            else
                Color(255, 84, 84, 255)
            var editable : Boolean = expandedstate==120
            Text(
                text = "${currentsubject.percentage.toInt()}%\n ",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 75.sp,
                color = Color.White,
                modifier = Modifier
                    .background(Brush.horizontalGradient(listOf(colorA, Color.White)))
                    .constrainAs(percentage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .clickable {
                        expandedstate = if (expandedstate == 80) 120 else 80
                        secondlast = last
                        last = currentsubject.subject
                    }
            )
            Text(
                text = currentsubject.subject,
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier
                    .constrainAs(subjectname) {
                        top.linkTo(parent.top , margin = 21.dp)
                        start.linkTo(parent.start , margin = 15.dp)
                    },
                style = TextStyle(letterSpacing = 1.sp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = currentsubject.fullname,
                color = Color(0, 0, 0, 133),
                fontSize = 10.sp,
                modifier = Modifier
                    .constrainAs(subjectfullname) {
                        top.linkTo(parent.top, margin = 45.dp)
                        start.linkTo(parent.start, margin = 15.dp)
                    }
                    .width(150.dp),
                style = TextStyle(letterSpacing = 1.sp),
                fontWeight = FontWeight.Bold
            )
            if(expandedstate==120){
                Text(
                    text = "Presents for $RequiredPercentage% : ${preq(currentsubject)}",
                    color = Color(0, 0, 0, 133),
                    fontSize = 10.sp,
                    modifier = Modifier
                        .constrainAs(remainingpresents) {
                            bottom.linkTo(parent.bottom, margin = 15.dp)
                            start.linkTo(parent.start, margin = 15.dp)
                        }
                        .width(150.dp),
                    style = TextStyle(letterSpacing = 1.sp),
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = currentsubject.absent.toString(),
                color = Color(240, 59, 82, 255),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(absentcount) {
                        top.linkTo(parent.top, margin = 22.dp)
                        end.linkTo(parent.end, margin = 136.dp)
                    }
                    .width(40.dp),
                style = TextStyle(letterSpacing = 1.sp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = currentsubject.present.toString(),
                color = Color(68, 174, 255, 255),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(presentcount) {
                        top.linkTo(parent.top, margin = 22.dp)
                        end.linkTo(parent.end, margin = 46.dp)
                    }
                    .width(40.dp),
                style = TextStyle(letterSpacing = 1.sp),
                fontWeight = FontWeight.Bold
            )
            AnimatedVisibility(visible = editable, enter = fadeIn(), exit = fadeOut(),
                modifier = Modifier
                    .constrainAs(presentbuttonpos) {
                        top.linkTo(presentcount.bottom, margin = 16.dp)
                        start.linkTo(presentcount.start, margin = 35.dp)
                    }
                    .clickable {
                        pere++
                        currentsubject.present = pere
                        currentsubject.percentage = percentage(currentsubject)
                        storedata(subjects, context)
                    }) {
                Text(text = "+",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                )
            }
            AnimatedVisibility(visible = editable, enter = fadeIn(), exit = fadeOut(),
                modifier = Modifier
                    .constrainAs(presentbuttonneg) {
                        top.linkTo(presentcount.bottom, margin = 16.dp)
                        end.linkTo(presentbuttonpos.start, margin = 35.dp)
                    }
                    .clickable {
                        if (pere > 0) {
                            pere--
                            currentsubject.present = pere
                            currentsubject.percentage = percentage(currentsubject)
                            storedata(subjects, context)
                        }
                    }) {
                Text(text = "-",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                )
            }
            AnimatedVisibility(visible = editable, enter = fadeIn(), exit = fadeOut(),
                modifier = Modifier
                    .constrainAs(absentbuttonpos) {
                        top.linkTo(absentcount.bottom, margin = 16.dp)
                        start.linkTo(absentcount.start, margin = 35.dp)
                    }
                    .clickable {
                        abse++
                        currentsubject.absent = abse
                        currentsubject.percentage = percentage(currentsubject)
                        storedata(subjects, context)
                    }) {
                Text(text = "+",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                )
            }
            AnimatedVisibility(visible = editable, enter = fadeIn(), exit = fadeOut(),
                modifier = Modifier
                    .constrainAs(absentbuttonneg) {
                        top.linkTo(absentcount.bottom, margin = 16.dp)
                        end.linkTo(absentbuttonpos.start, margin = 35.dp)
                    }
                    .clickable {
                        if (abse > 0) {
                            abse--
                            currentsubject.absent = abse
                            currentsubject.percentage = percentage(currentsubject)
                            storedata(subjects, context)
                        }
                    }) {
                Text(text = "-",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                )
            }
            AnimatedVisibility(visible = editable, enter = fadeIn(), exit = fadeOut(),
                modifier = Modifier
                    .width(100.dp)
                    .height(15.dp)
                    .constrainAs(absenttext) {
                        top.linkTo(parent.top, margin = 10.dp)
                        end.linkTo(parent.end, margin = 106.dp)
                    }
            ) {
                Text(text = "ABSENTS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                )
            }
            AnimatedVisibility(visible = editable, enter = fadeIn(), exit = fadeOut(),
                modifier = Modifier
                    .width(100.dp)
                    .height(15.dp)
                    .constrainAs(presenttext) {
                        top.linkTo(parent.top, margin = 10.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
            ) {
                Text(text = "PRESENTS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                )
            }
        }
    }
}
