package com.example.mobile_moviescatalog2023.View.SplashScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    state: SplashContract.State,
    onEventSent: (event: SplashContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: SplashContract.Effect.Navigation) -> Unit
) {
    FilmusTheme {
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

        val systemUiController = rememberSystemUiController()
        val barsColor = MaterialTheme.colorScheme.background
        DisposableEffect(barsColor) {
            systemUiController.setStatusBarColor(Color.Black)
            systemUiController.setNavigationBarColor(Color.Black)
            onDispose {
                systemUiController.setStatusBarColor(barsColor)
                systemUiController.setNavigationBarColor(barsColor)
            }
        }

        LaunchedEffect(Unit) {
            onEventSent(SplashContract.Event.GetToken)
        }

        if(!state.isTryingGetToken and state.isSuccessGetToken) {
            onNavigationRequested(SplashContract.Effect.Navigation.ToMain)
        } else if(!state.isTryingGetToken and !state.isSuccessGetToken) {
            onNavigationRequested(SplashContract.Effect.Navigation.ToIntroducingScreen)
        }
    }
}