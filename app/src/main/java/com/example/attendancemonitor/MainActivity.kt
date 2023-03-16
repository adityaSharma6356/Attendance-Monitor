package com.example.attendancemonitor

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendancemonitor.ui.theme.AttendanceMonitorTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttendanceMonitorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0, 7, 43, 255)
                ) {
                    subjects = dataLoader(this )
                    MainList()
                }
            }
        }
    }
}


var subjects = mutableStateListOf<data>(
    data("DBMS" , 0 , 0 , 100f),
    data("JAVA" , 0 , 0 , 100f),
    data("TOC" , 0 , 0 , 100f),
    data("PSLP" , 0 , 0 , 100f),
    data("C & S" , 0 , 0 , 100f),
    data("TW" , 0 , 0 , 100f),
)

data class data(
    var subject : String,
    var absent : Int ,
    var present : Int ,
    var percentage : Float
)


@Composable
fun MainList() {
    Surface(modifier = Modifier
        .fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            subjects.forEach {
                Singleblock(it)
            }
        }
    }
}

@Composable
fun Singleblock(subject : data ) {
    val context = LocalContext.current
    Card(modifier = Modifier
        .padding(15.dp)
        .fillMaxWidth()
        .height(250.dp),
    backgroundColor = Color(30, 39, 73, 255),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxSize()) {
            var pere by remember { mutableStateOf(value = subject.present) }
            var abse by remember { mutableStateOf(value = subject.absent) }

            subject.absent = abse
            subject.present = pere
            Row(horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
                Text(
                    text = subject.subject,
                    fontSize = 20.sp,
                    color = Color(83, 113, 255, 255),
                    modifier = Modifier
                        .padding(10.dp, 5.dp, 0.dp, 0.dp),
                )
                Text(
                    text = "${subject.percentage}" +"%",
                    color = Color(83, 113, 255, 255),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(0.dp , 5.dp , 10.dp , 0.dp)

                )
            }
            Row {
                Text(
                    text = "Days Absent : ${subject.absent}\nDays Present : ${subject.present}\nPresents Required for 75% : ${preq(subject)}",
                    color = Color(83, 113, 255, 255),
                    modifier = Modifier
                        .padding(10.dp , 0.dp , 0.dp , 0.dp)
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier
                    .height(120.dp)
                    .width(50.dp)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)) {
                    Button(shape = RoundedCornerShape(5.dp) ,
                        onClick = {
                            abse++
                            subject.absent = abse
                            subject.percentage = percentage(subject)
                            storedata(subjects , context)
                    } ,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(71, 0, 0, 255))) {
                        Text(text = "+", color = Color.White)
                    }
                    Text(text = "A", color = Color.White, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Button(shape = RoundedCornerShape(5.dp) ,
                        onClick = {
                            abse--
                            subject.absent = abse
                            subject.percentage = percentage(subject)
                            if(abse>=0)storedata(subjects , context)
                        } ,
                        ) {
                        Text(text = "-", color = Color.White)
                    }
                }
                Column(modifier = Modifier
                    .height(120.dp)
                    .width(50.dp)
                    .padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                    Button(shape = RoundedCornerShape(5.dp) ,
                        onClick = {
                            pere++
                            subject.present = pere
                            subject.percentage = percentage(subject)
                            storedata(subjects , context)
                    } ,
                        ) {
                        Text(text = "+", color = Color.White)
                    }
                    Text(text = "P", color = Color.White, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Button(shape = RoundedCornerShape(5.dp) ,
                        onClick = {
                            pere--
                            subject.present = pere
                            subject.percentage = percentage(subject)
                            if(pere>=0)storedata(subjects , context)
                                  },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(71, 0, 0, 255))
                        ) {
                        Text(text = "-", color = Color.White)
                    }
                }
            }
        }
    }
}

fun percentage(it : data) : Float{
    if(it.present+it.absent==0) return 0f
    val pres : Float = it.present.toFloat()
    val abs : Float = it.absent.toFloat()
    val per : Float = (pres*100/(abs + pres))
    return per
}

fun preq(it : data) : Int{
    if(it.percentage >= 75f) return 0
    return 3*it.absent - it.present
}

fun storedata (it : List<data> , context: Context) {
    val sharedpef = context.getSharedPreferences("attendancedata" , Context.MODE_PRIVATE)
    val editor = sharedpef.edit()
    val gson = Gson()
    val json = gson.toJson(it)
    editor.putString("atdata" , json)
    editor.apply()
}

fun dataLoader(context: Context , ) : SnapshotStateList<data>{
    val shared = context.getSharedPreferences("attendancedata" , Context.MODE_PRIVATE)

    val json = shared.getString("atdata" , null)

    if(json!=null){
        val turnsType = object : TypeToken<SnapshotStateList<data>>(){}.type
        val turns = Gson().fromJson<SnapshotStateList<data>>(json , turnsType)
        return turns
    }
    return subjects
}












