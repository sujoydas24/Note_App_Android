package com.example.noteapp.Navigation

sealed class NoteNavigationItem(val route: String) {
    object SplashScreen : NoteNavigationItem(route = "splash")
    object HomeScreen : NoteNavigationItem(route = "home")
    object InsertNoteScreen : NoteNavigationItem(route = "insert")
}