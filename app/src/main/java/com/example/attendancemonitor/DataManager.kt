package com.example.attendancemonitor

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun storedata (it : List<Daata>, context: Context) {
    val sharedpef = context.getSharedPreferences("attendancedatanew" , Context.MODE_PRIVATE)
    val editor = sharedpef.edit()
    val gson = Gson()
    val json = gson.toJson(it)
    editor.putString("atdatanew" , json)
    editor.apply()
}
fun storedata (it : Int , context: Context) {
    val sharedpef = context.getSharedPreferences("attendanceperc" , Context.MODE_PRIVATE)
    val editor = sharedpef.edit()
    editor.putInt("atdatanperc" , it)
    editor.apply()
}

fun percLoader(context: Context): Int {
    val shared = context.getSharedPreferences("attendanceperc", Context.MODE_PRIVATE)
    return shared.getInt("atdatanperc", 75)

}
fun dataLoader(context: Context) : SnapshotStateList<Daata> {
    val shared = context.getSharedPreferences("attendancedatanew" , Context.MODE_PRIVATE)

    val json = shared.getString("atdatanew" , null)

    if(json!=null) {
        val turnsType = object : TypeToken<SnapshotStateList<Daata>>() {}.type
        return Gson().fromJson(json, turnsType)
    }
    return subjects
}