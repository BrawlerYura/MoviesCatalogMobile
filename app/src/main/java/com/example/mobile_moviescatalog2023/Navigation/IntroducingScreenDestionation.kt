package com.example.mobile_moviescatalog2023.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.View.IntroducingScreen.IntroducingContract
import com.example.mobile_moviescatalog2023.View.IntroducingScreen.IntroducingScreen
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationPasswordScreen.RegistrationPasswordViewModel
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen.RegistrationPasswordScreen
import org.koin.androidx.compose.getViewModel

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