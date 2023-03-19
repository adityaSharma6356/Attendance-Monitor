package com.example.attendancemonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.attendancemonitor.ui.theme.AttendanceMonitorTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttendanceMonitorTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color(250, 76, 0, 255),
                        darkIcons = false
                    )
                }
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color(230, 226, 226, 255)
                ) {
                    subjects = dataLoader(this )
                    RequiredPercentage = percLoader(this)
                    MainList()
                }
            }
        }
    }
}











