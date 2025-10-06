package com.example.noteapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.noteapp.Models.Notes
import com.example.noteapp.ui.theme.colorBlack
import com.example.noteapp.ui.theme.colorGrey
import com.example.noteapp.ui.theme.colorLightGrey
import com.example.noteapp.ui.theme.colorRed
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.noteapp.Navigation.NoteNavigationItem
import com.example.noteapp.Navigation.NotesNavigation
import com.google.firebase.firestore.CollectionReference


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun NoteScreen(navHostController: NavHostController) {

    val db = FirebaseFirestore.getInstance()
    val notesDB = db.collection("notes")

    val notesList = remember {
        mutableStateOf(listOf<Notes>())
    }
    val dataValue = remember {
        mutableStateOf( false )
    }

    LaunchedEffect(Unit) {
        notesDB.addSnapshotListener  {
            value, error ->
            if (error == null && value != null) {
                val data = value.toObjects(Notes::class.java)
                notesList.value = data
                dataValue.value = true

            }else{
                dataValue.value = false
            }
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(containerColor = colorRed, onClick = {                             //Insert Data

            navHostController.navigate(NoteNavigationItem.InsertNoteScreen.route)

        }) {
            Icon(imageVector = Icons.Default.Add,
                contentDescription = "Add")
        }
    }) { innerPadding ->

        Box(modifier = Modifier
            .padding(innerPadding)
            .background(colorBlack)
            .fillMaxSize())
        {

            Column(modifier = Modifier.padding(15.dp)){
                Text(text = "Create Note\n",
                    style = TextStyle(fontSize = 32.sp),
                    color = Color.White,
                    fontWeight= FontWeight.Bold)
                if(dataValue.value){
                    LazyColumn {
                        items(notesList.value) { notes ->
                            ListItem(note = notes, notesDB = notesDB)
                        }

//                    }else{
//                            Box(modifier = Modifier.fillMaxSize()){
//                                CircularProgressIndicator(
//                                    modifier = Modifier
//                                        .size(25.dp)
//                                        .align(Alignment.Center)
//                                )
//                            }
//                    }
                }

                }
            }

        }
    }
}



@Composable
fun ListItem(note: Notes, notesDB: CollectionReference) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)
        .background(color = colorGrey)
        .padding(10.dp)
//        .height(20.dp)
    )
    {
        DropdownMenu(modifier = Modifier.background(color = Color.White),
            properties = PopupProperties(clippingEnabled = true),
            offset = DpOffset(x=(-40).dp, y=0.dp),
            expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text(text = "Update", style = TextStyle(colorGrey)) } , onClick = {                           //Update
//                navHostController.navigate(NotesNavigationItem.InsertNotesScreen.route)
                expanded=false
            } )
            DropdownMenuItem(text = { Text(text = "Delete", style = TextStyle(colorGrey)) } , onClick = {                           //Delete
                    notesDB.document(note.id).delete()
            } )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = " ",
            tint = Color.White,
            modifier = Modifier.align(Alignment.TopEnd)
                .padding(10.dp)
                .clickable{
                    expanded = true
                }
        )
        Column (modifier = Modifier.padding(10.dp)){
            Text( text = note.title, style = TextStyle(color = Color.White))
            Text( text = note.description, style = TextStyle(color = colorLightGrey))
        }
    }
}