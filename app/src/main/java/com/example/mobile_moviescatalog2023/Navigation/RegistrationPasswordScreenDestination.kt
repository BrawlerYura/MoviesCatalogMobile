package com.example.mobile_moviescatalog2023.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationPasswordScreen.RegistrationPasswordContract
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationPasswordScreen.RegistrationPasswordViewModel
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
                is RegistrationPasswordContract.Effect.Navigation.ToMain -> { }

                is RegistrationPasswordContract.Effect.Navigation.ToLogin -> {
                    navController.navigate(NavigationModel.MainScreens.LoginScreen.name)
                }

                is RegistrationPasswordContract.Effect.Navigation.Back -> {
                    navController.popBackStack()
                }
            }
        }
    )
}