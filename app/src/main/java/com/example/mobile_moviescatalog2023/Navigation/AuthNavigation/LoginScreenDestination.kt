package com.example.mobile_moviescatalog2023.Navigation.AuthNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.Composables.LoginScreen
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreenDestination(navController: NavHostController) {
    val viewModel = getViewModel<LoginViewModel>()
    LoginScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested =  { navigationEffect ->
            when (navigationEffect) {
                is LoginContract.Effect.Navigation.ToMain -> {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(0)
                    }
                }

                is LoginContract.Effect.Navigation.ToRegistration -> {
                    navController.navigate(Screen.Registration.route)
                }

                is LoginContract.Effect.Navigation.Back -> {
                    navController.navigateUp()
                }
            }
        }
    )
}