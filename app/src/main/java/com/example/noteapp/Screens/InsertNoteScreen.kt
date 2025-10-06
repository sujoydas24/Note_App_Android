package com.example.noteapp.Screens

import android.R
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.ui.theme.colorBlack
import com.example.noteapp.ui.theme.colorGrey
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.noteapp.ui.theme.colorLightGrey
import com.example.noteapp.ui.theme.colorRed
import com.google.firebase.firestore.FirebaseFirestore
import com.example.noteapp.Models.Notes

@Composable

fun InsertNoteScreen(navHostController: NavHostController) {

    val context = LocalContext.current

    val db = FirebaseFirestore.getInstance()
    val notesDB = db.collection("notes")

    val title= remember {
        mutableStateOf("")
    }
    val description= remember {
        mutableStateOf("")
    }

//    var title by remember { mutableStateOf("") }
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                if(title.value.isEmpty() && description.value.isEmpty()) {
                    Toast.makeText(context, "Enter", Toast.LENGTH_SHORT).show()
                }else{
                    val id  =  notesDB.document().id
                    val notes  = Notes(
                        id = id,
                        title = title.value,
                        description = description.value
                    )


                    notesDB.document(id).set(notes).addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(context, "Done this", Toast.LENGTH_SHORT).show()
                            navHostController.popBackStack()

                        }else{
                                Toast.makeText(context, "something Wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            },
            containerColor = colorRed,
            contentColor = Color.White,
        ) {
            Icon(imageVector = Icons.Default.Done,
                contentDescription = "done")
        }
    })


    { innerPadding ->

        Box(modifier = Modifier
            .padding(innerPadding)
            .background(colorBlack)
            .fillMaxSize())
        {

            Column(modifier = Modifier.padding(15.dp)){
                Text(text = "Insert Note\n",
                    style = TextStyle(fontSize = 32.sp),
                    color = Color.White,
                    fontWeight= FontWeight.Bold)
//..........................................................................title
                Spacer(modifier = Modifier.height(15.dp))
                TextField(textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorGrey,
                        unfocusedContainerColor = colorGrey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent),
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text(text = "Title",
                            style = TextStyle(fontSize = 18.sp, color = colorLightGrey)) },
                    modifier = Modifier.fillMaxWidth(),
                )
//..........................................................................description
                Spacer(modifier = Modifier.height(15.dp))
                TextField(textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorGrey,
                        unfocusedContainerColor = colorGrey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent),
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text(text = "Description",
                              style = TextStyle(fontSize = 18.sp, color = colorLightGrey))},
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight(0.6f),
                )

                }
            }

        }
}