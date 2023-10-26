package com.example.mobile_moviescatalog2023.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen.RegistrationContract
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen.RegistrationScreen
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen.RegistrationViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun RegistratinoScreenDestination(navController: NavHostController) {
    val viewModel = getViewModel<RegistrationViewModel>()
    RegistrationScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is RegistrationContract.Effect.Navigation.NextScreen -> {
                    navController.navigate(Screen.RegPass.route)
                }

                is RegistrationContract.Effect.Navigation.ToLogin -> {
                    navController.navigate(Screen.Login.route)
                }

                is RegistrationContract.Effect.Navigation.Back -> {
                    navController.popBackStack()
                }
            }
        }
    )
}