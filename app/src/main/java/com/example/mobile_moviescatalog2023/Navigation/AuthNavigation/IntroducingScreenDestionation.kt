package com.example.mobile_moviescatalog2023.Navigation.AuthNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.View.AuthScreens.IntroducingScreen.IntroducingContract
import com.example.mobile_moviescatalog2023.View.AuthScreens.IntroducingScreen.Composables.IntroducingScreen

@Composable
fun IntroducingScreenDestination(navController: NavHostController) {
    IntroducingScreen(
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is IntroducingContract.Effect.Navigation.ToRegistration -> {
                    navController.navigate(Screen.Registration.route)
                }

                is IntroducingContract.Effect.Navigation.ToLogin -> {
                    navController.navigate(Screen.Login.route)
                }
            }
        }
    )
}