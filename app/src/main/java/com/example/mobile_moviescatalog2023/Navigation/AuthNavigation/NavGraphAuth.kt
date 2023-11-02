package com.example.mobile_moviescatalog2023.Navigation.AuthNavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies.RegisterRequestBody


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

        composable(
            Screen.RegPass.route + "/{userName}/{name}/{email}/{birthDate}/{gender}",
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("birthDate") { type = NavType.StringType },
                navArgument("gender") { type = NavType.IntType }
            )
        ) {entry ->
            val registerRequestBody = RegisterRequestBody(
                userName = (entry.arguments?.getString("userName") ?: ""),
                name = (entry.arguments?.getString("userName") ?: ""),
                password = "",
                email = (entry.arguments?.getString("email") ?: ""),
                birthDate = (entry.arguments?.getString("birthDate") ?: ""),
                gender = (entry.arguments?.getInt("gender") ?: 0)
            )
            RegistrationPasswordScreenDestination(navController, registerRequestBody)
        }
    }
    composable(Screen.Splash.route) {
        SplashScreenDestination(navController)
    }
}