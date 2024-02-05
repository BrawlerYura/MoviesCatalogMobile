package com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.View.Base.SIDE_EFFECTS_KEY
import com.example.mobile_moviescatalog2023.View.Common.NetworkErrorScreen
import com.example.mobile_moviescatalog2023.ui.theme.FilmusTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

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
                is SplashContract.Effect.Navigation.ToIntroducingScreen -> onNavigationRequested(
                    effect
                )
            }
        }?.collect()
    }

    when {
        state.isNetworkError -> {
            NetworkErrorScreen {
                onEventSent(SplashContract.Event.GetToken)
            }
        }

        state.isError -> onEventSent(SplashContract.Event.OnTokenLoadedFailed)
        else -> {
            FilmusTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
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
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        state = SplashContract.State(
            isNetworkError = false,
            isError = false
        ),
        onEventSent = { },
        effectFlow = null,
        onNavigationRequested = { }
    )
}