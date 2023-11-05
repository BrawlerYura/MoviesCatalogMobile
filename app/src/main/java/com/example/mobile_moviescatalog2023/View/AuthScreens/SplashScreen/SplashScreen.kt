package com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    state: SplashContract.State,
    onEventSent: (event: SplashContract.Event) -> Unit,
    effectFlow: Flow<SplashContract.Effect>?,
    onNavigationRequested: (navigationEffect: SplashContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is SplashContract.Effect.Navigation.ToMain -> onNavigationRequested(effect)
                is SplashContract.Effect.Navigation.ToIntroducingScreen -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    FilmusTheme {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
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

        when {
            state.isSuccessGetToken -> onEventSent(SplashContract.Event.OnTokenLoadedSuccess)
            state.isError -> onEventSent(SplashContract.Event.OnTokenLoadedFailed)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        state = SplashContract.State (
            isSuccessGetToken = false,
            isError = false
        ),
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { }
    )
}