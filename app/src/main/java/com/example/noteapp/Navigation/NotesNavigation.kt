package com.example.noteapp.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.noteapp.Screens.InsertNoteScreen
import com.example.noteapp.Screens.NoteScreen
import com.example.noteapp.Screens.SplashScreen



@Composable
fun NotesNavigation(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = "splash"){
        composable(NoteNavigationItem.SplashScreen.route){
            SplashScreen(navHostController)
        }

        composable(NoteNavigationItem.HomeScreen.route){
            NoteScreen(navHostController)
        }
        composable(NoteNavigationItem.InsertNoteScreen.route){
            InsertNoteScreen(navHostController)
        }

    }
}


