package com.example.mobile_moviescatalog2023.View.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.R

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painterResource(R.drawable.launch_screen_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
            )
        Image(
            painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}