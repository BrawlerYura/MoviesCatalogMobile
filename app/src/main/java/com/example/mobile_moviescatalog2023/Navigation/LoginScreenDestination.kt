package com.example.mobile_moviescatalog2023.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginScreen
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreenDestination(navController: NavHostController) {
    val viewModel = getViewModel<LoginViewModel>()
    LoginScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        onNavigationRequested =  { navigationEffect ->
            when (navigationEffect) {
                is LoginContract.Effect.Navigation.SignIn -> {
                    navController.navigate(NavigationModel.MainScreens.LoginScreen.name)
                }

                is LoginContract.Effect.Navigation.SignUp -> {
                    navController.navigate(NavigationModel.MainScreens.RegistrationScreen.name)
                }

                is LoginContract.Effect.Navigation.Back -> {
                    navController.popBackStack()
                }
            }
        }
    )
}