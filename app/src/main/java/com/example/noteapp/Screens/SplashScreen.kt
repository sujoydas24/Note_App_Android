package com.example.noteapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.noteapp.Navigation.NoteNavigationItem
import com.example.noteapp.R
import com.example.noteapp.ui.theme.colorBlack
import kotlinx.coroutines.delay

@Composable

fun SplashScreen (navHostController: NavHostController) {
    Scaffold { innerPadding ->
        Box(modifier= Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(color = colorBlack)
        ) {
            Image(painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier= Modifier.size(150.dp).align(Alignment.Center)
            )
        }
    }
    LaunchedEffect(Unit) {
        delay(1500)
        navHostController.navigate(NoteNavigationItem.HomeScreen.route){
            popUpTo(NoteNavigationItem.SplashScreen.route){
                inclusive = true
            }
        }
    }

}