package com.example.attendancemonitor

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.attendancemonitor.ui.theme.AttendanceMonitorTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttendanceMonitorTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color(230, 226, 226, 255)
                ) {
                    subjects = dataLoader(this )
                    MainList()
                }
            }
        }
    }
}


var subjects = mutableStateListOf(
    Daata("DBMS" ,"Data Base Management System", 0 , 0 , 100f),
    Daata("JAVA" ,"JAVA", 0 , 0 , 100f),
    Daata("TOC" ,"Theory of Computation", 0 , 0 , 100f),
    Daata("PSLP" ,"Probability Statistics and Linear Programming", 0 , 0 , 100f),
    Daata("C & S" ,"Circuits and System" , 0 , 0 , 100f),
    Daata("TW" ,"Technical Writing", 0 , 0 , 100f),
)

data class Daata(
    var subject : String,
    var fullname : String,
    var absent : Int ,
    var present : Int ,
    var percentage : Float
)

var colorslist = listOf(
    Color(255, 0, 132, 255),
    Color(250, 76, 0, 255),
)

@Composable
fun MainList() {
    Column {
        Surface(
            modifier = Modifier
                .height(80.dp)
                .background(Brush.horizontalGradient(colorslist)),
            color = Color.Transparent
        ) {
            Text(
                text = "ATTENDANCE\nMONITOR",
                fontSize = 19.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(letterSpacing = 3.sp)
            )
        }
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
    }
}

var last : String = ""
var secondlast : String = ""

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
                presenttext
            ) = createRefs()
            val colorA : Color = if(currentsubject.percentage>=75)
                Color(64, 255, 225, 255)
            else if (currentsubject.percentage>=50)
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
                text = "${currentsubject.fullname}\n\n\nPresents for 75% : ${preq(currentsubject)}",
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
            Text(
                text = currentsubject.absent.toString(),
                color = Color(240, 59, 82, 255),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(absentcount) {
                        top.linkTo(parent.top, margin = 22.dp)
                        start.linkTo(parent.start, margin = 220.dp)
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
                        start.linkTo(parent.start, margin = 310.dp)
                    }
                    .width(40.dp),
                style = TextStyle(letterSpacing = 1.sp),
                fontWeight = FontWeight.Bold
            )
            AnimatedVisibility(visible = editable,
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
            AnimatedVisibility(visible = editable,
            modifier = Modifier
                .constrainAs(presentbuttonneg) {
                        top.linkTo(presentcount.bottom , margin = 16.dp)
                        end.linkTo(presentbuttonpos.start , margin = 35.dp)
                    }
                .clickable {
                    if(pere>0) {
                        pere--
                        currentsubject.present = pere
                        currentsubject.percentage = percentage(currentsubject)
                        storedata(subjects , context)
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
            AnimatedVisibility(visible = editable,
            modifier = Modifier
                .constrainAs(absentbuttonpos) {
                        top.linkTo(absentcount.bottom , margin = 16.dp)
                        start.linkTo(absentcount.start , margin = 35.dp)
                    }
                .clickable {
                    abse++
                    currentsubject.absent = abse
                    currentsubject.percentage = percentage(currentsubject)
                    storedata(subjects , context)
                }) {
                Text(text = "+",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                modifier = Modifier
                )
            }
            AnimatedVisibility(visible = editable,
            modifier = Modifier
                .constrainAs(absentbuttonneg) {
                        top.linkTo(absentcount.bottom , margin = 16.dp)
                        end.linkTo(absentbuttonpos.start , margin = 35.dp)
                    }
                .clickable {
                    if(abse>0) {
                        abse--
                        currentsubject.absent = abse
                        currentsubject.percentage = percentage(currentsubject)
                        storedata(subjects , context) }
                        }) {
                Text(text = "-",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                modifier = Modifier
                )
            }
            AnimatedVisibility(visible = editable,
            modifier = Modifier
                .width(100.dp)
                .height(15.dp)
                .constrainAs(absenttext) {
                        top.linkTo(parent.top , margin = 10.dp)
                        end.linkTo(parent.end , margin = 106.dp)
                    }
            ) {
                Text(text = "ABSENTS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                modifier = Modifier
                )
            }
            AnimatedVisibility(visible = editable,
            modifier = Modifier
                .width(100.dp)
                .height(15.dp)
                .constrainAs(presenttext) {
                        top.linkTo(parent.top , margin = 10.dp)
                        end.linkTo(parent.end , margin = 16.dp)
                    }
            ) {
                Text(text = "PRESENTS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                modifier = Modifier
                )
            }
        }
    }
}

fun percentage(it: Daata): Float {
    if (it.present + it.absent == 0) return 0f
    val pres: Float = it.present.toFloat()
    val abs: Float = it.absent.toFloat()
    return (pres * 100 / (abs + pres))
}

fun preq(it : Daata) : Int{
    if(it.percentage >= 75f) return 0
    return 3*it.absent - it.present
}

fun storedata (it : List<Daata>, context: Context) {
    val sharedpef = context.getSharedPreferences("attendancedatanew" , Context.MODE_PRIVATE)
    val editor = sharedpef.edit()
    val gson = Gson()
    val json = gson.toJson(it)
    editor.putString("atdatanew" , json)
    editor.apply()
}

fun dataLoader(context: Context) : SnapshotStateList<Daata>{
    val shared = context.getSharedPreferences("attendancedatanew" , Context.MODE_PRIVATE)

    val json = shared.getString("atdatanew" , null)

    if(json!=null) {
        val turnsType = object : TypeToken<SnapshotStateList<Daata>>() {}.type
        return Gson().fromJson(json, turnsType)
    }
    return subjects
}












