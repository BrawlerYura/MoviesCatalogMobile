package com.example.mobile_moviescatalog2023.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.View.SplashScreen.SplashContract
import com.example.mobile_moviescatalog2023.View.SplashScreen.SplashScreen
import com.example.mobile_moviescatalog2023.View.SplashScreen.SplashScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreenDestination(navController: NavHostController) {
    val viewModel = getViewModel<SplashScreenViewModel>()
    SplashScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is SplashContract.Effect.Navigation.ToIntroducingScreen -> {
                    navController.popBackStack()
                    navController.navigate(Screen.Introducing.route)
                }
                is SplashContract.Effect.Navigation.ToMain -> {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                }
            }
        }
    )
}