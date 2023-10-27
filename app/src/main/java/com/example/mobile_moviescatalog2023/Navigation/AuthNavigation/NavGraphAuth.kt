package com.example.mobile_moviescatalog2023.Navigation.AuthNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreen


fun NavGraphBuilder.NavGraphAuth(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Splash.route,
        route = "authentication"
    ) {
        composable(Screen.Splash.route) {
            SplashScreenDestination(navController)
        }
        composable(Screen.Introducing.route) {
            IntroducingScreenDestination(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreenDestination(navController)
        }

        composable(Screen.Registration.route) {
            RegistratinoScreenDestination(navController)
        }

        composable(Screen.RegPass.route) {
            RegistrationPasswordScreenDestination(navController)
        }
    }
    composable(Screen.Splash.route) {
        SplashScreenDestination(navController)
    }
}