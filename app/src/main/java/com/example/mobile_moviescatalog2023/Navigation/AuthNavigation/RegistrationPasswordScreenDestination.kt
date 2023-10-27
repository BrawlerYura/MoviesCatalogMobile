package com.example.mobile_moviescatalog2023.Navigation.AuthNavigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen.RegistrationPasswordViewModel
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen.RegistrationPasswordScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun RegistrationPasswordScreenDestination(navController: NavHostController) {
    val viewModel = getViewModel<RegistrationPasswordViewModel>()
    RegistrationPasswordScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is RegistrationPasswordContract.Effect.Navigation.ToMain -> {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                }

                is RegistrationPasswordContract.Effect.Navigation.ToLogin -> {
                    navController.navigate(Screen.Login.route)
                }

                is RegistrationPasswordContract.Effect.Navigation.Back -> {
                    navController.navigateUp()
                }
            }
        }
    )
}