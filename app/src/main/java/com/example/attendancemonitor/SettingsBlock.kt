package com.example.attendancemonitor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun SettingsBlock() {
    val context = LocalContext.current
    var openDialog by remember { mutableStateOf(false) }
    var openDialog2 by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { openDialog = true }
            ) {
        Text(text = "  Set Subject Names " , modifier = Modifier.fillMaxWidth() , fontWeight = FontWeight.Bold)
    }
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { openDialog2 = true }
            ) {
        Text(text = "  Set Subject Full Names " , modifier = Modifier.fillMaxWidth() , fontWeight = FontWeight.Bold)
    }
    if (openDialog) {
        var text6 by remember { mutableStateOf(TextFieldValue(subjects[5].subject)) }
        var text5 by remember { mutableStateOf(TextFieldValue(subjects[4].subject)) }
        var text4 by remember { mutableStateOf(TextFieldValue(subjects[3].subject)) }
        var text3 by remember { mutableStateOf(TextFieldValue(subjects[2].subject)) }
        var text2 by remember { mutableStateOf(TextFieldValue(subjects[1].subject)) }
        var text1 by remember { mutableStateOf(TextFieldValue(subjects[0].subject)) }
        AlertDialog(
            onDismissRequest = {
                openDialog = false
            },
            text = {
                Column {
                    TextField(
                        label = {
                                Text(text = "Subject 1 name ")
                        },
                        value = text1,
                        onValueChange = { text1 = it }
                    )

                    TextField(
                        label = {
                                Text(text = "Subject 2 name ")
                        },
                        value = text2,
                        onValueChange = { text2 = it }
                    )

                    TextField(
                        label = {
                                Text(text = "Subject 3 name ")
                        },
                        value = text3,
                        onValueChange = { text3 = it }
                    )

                    TextField(
                        label = {
                                Text(text = "Subject 4 name ")
                        },
                        value = text4,
                        onValueChange = { text4 = it }
                    )

                    TextField(
                        label = {
                                Text(text = "Subject 5 name ")
                        },
                        value = text5,
                        onValueChange = { text5 = it }
                    )

                    TextField(
                        label = {
                                Text(text = "Subject 6 name ")
                        },
                        value = text6,
                        onValueChange = { text6 = it }
                    )
                }
            },
            buttons = {
                Column(
                    modifier = Modifier.padding(all = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { openDialog = false }
                    ) {
                        Text("Dismiss")
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            subjects[0].subject = text1.text
                            subjects[1].subject = text2.text
                            subjects[2].subject = text3.text
                            subjects[3].subject = text4.text
                            subjects[4].subject = text5.text
                            subjects[5].subject = text6.text
                            storedata(subjects , context)
                            openDialog = false }
                    ) {
                        Text("SAVE")
                    }
                }
            }
        )
    }
    if (openDialog2) {
        var text6 by remember { mutableStateOf(TextFieldValue(subjects[5].fullname)) }
        var text5 by remember { mutableStateOf(TextFieldValue(subjects[4].fullname)) }
        var text4 by remember { mutableStateOf(TextFieldValue(subjects[3].fullname)) }
        var text3 by remember { mutableStateOf(TextFieldValue(subjects[2].fullname)) }
        var text2 by remember { mutableStateOf(TextFieldValue(subjects[1].fullname)) }
        var text1 by remember { mutableStateOf(TextFieldValue(subjects[0].fullname)) }
        AlertDialog(
            onDismissRequest = {
                openDialog2 = false
            },
            text = {
                Column {
                    TextField(
                        label = {
                                Text(text = "Subject 1 full name ")
                        },
                        value = text1,
                        onValueChange = { text1 = it }
                    )

                    TextField(
                        label = {
                                Text(text = "Subject 2 full name ")
                        },
                        value = text2,
                        onValueChange = { text2 = it }
                    )

                    TextField(
                        label = {
                                Text(text = "Subject 3 full name ")
                        },
                        value = text3,
                        onValueChange = { text3 = it }
                    )

                    TextField(
                        label = {
                                Text(text = "Subject 4 full name ")
                        },
                        value = text4,
                        onValueChange = { text4 = it }
                    )

                    TextField(
                        label = {
                                Text(text = "Subject 5 full name ")
                        },
                        value = text5,
                        onValueChange = { text5 = it }
                    )

                    TextField(
                        label = {
                                Text(text = "Subject 6 full name ")
                        },
                        value = text6,
                        onValueChange = { text6 = it }
                    )
                }
            },
            buttons = {
                Column(
                    modifier = Modifier.padding(all = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { openDialog2 = false }
                    ) {
                        Text("Dismiss")
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            subjects[0].fullname = text1.text
                            subjects[1].fullname = text2.text
                            subjects[2].fullname = text3.text
                            subjects[3].fullname = text4.text
                            subjects[4].fullname = text5.text
                            subjects[5].fullname = text6.text
                            storedata(subjects , context)
                            openDialog2 = false }
                    ) {
                        Text("SAVE")
                    }
                }
            }
        )
    }

}