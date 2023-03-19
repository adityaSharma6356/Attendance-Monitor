package com.example.attendancemonitor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.attendancemonitor.ui.theme.best


@Composable
fun MainList() {
    var change by remember { mutableStateOf(true) }
    Column {
        Surface(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(Brush.horizontalGradient(colorslist)),
            color = Color.Transparent
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val boxonecolor : Color = if(change){
                    Color(252, 40, 63, 0)
                } else {
                    Color(255, 196, 196, 255)
                }
                val (
                    attendancetitle,
                    settingstitle
                ) = createRefs()
                Box(modifier = Modifier
                    .height(30.dp)
                    .constrainAs(attendancetitle){
                        top.linkTo(parent.top , margin = 30.dp)
                        start.linkTo(parent.start , margin = 40.dp)
                    }
                    .background(boxonecolor, RoundedCornerShape(50)),
                    contentAlignment = Alignment.Center)
                {
                    val textcolor : Color = if(!change){
                        Color(252, 40, 63, 255)
                    } else {
                        Color(255, 196, 196, 255)
                    }
                    Text(
                        text = " ATTENDANCE ",
                        fontSize = 16.sp,
                        fontFamily = best,
                        color = textcolor,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable { change = false },
                        style = TextStyle(letterSpacing = 3.sp)
                    )
                }
                val boxtwocolor : Color = if(!change){
                    Color(252, 40, 63, 0)
                } else {
                    Color(255, 196, 196, 255)
                }
                Box(modifier = Modifier
                    .height(30.dp)
                    .constrainAs(settingstitle){
                        top.linkTo(parent.top , margin = 30.dp)
                        end.linkTo(parent.end , margin = 40.dp)
                    }
                    .background(boxtwocolor, RoundedCornerShape(50)),
                    contentAlignment = Alignment.Center)
                {
                    val textcolor : Color = if(change){
                        Color(252, 40, 63, 255)
                    } else {
                        Color(255, 196, 196, 255)
                    }
                    Text(
                        text = "  SETTINGS  ",
                        fontSize = 16.sp,
                        color = textcolor,
                        fontFamily = best,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable { change = true },
                        style = TextStyle(letterSpacing = 3.sp)
                    )
                }
            }
        }
        if(!change){
            Surface {
                val localDensity = LocalDensity.current
                var columnHeightDp by remember {
                    mutableStateOf(0.dp)
                }
                Column(modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        columnHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                    }
                    .background(Color(221, 221, 221, 255))
                    .verticalScroll(rememberScrollState())) {
                    subjects.forEach {
                        Singleblock(it , if(columnHeightDp>616.dp)700.dp else 600.dp)
                    }
                }
            }
        } else {
            SettingsBlock()
        }
    }
}


