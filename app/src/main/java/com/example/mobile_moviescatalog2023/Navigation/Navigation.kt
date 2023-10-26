package com.example.mobile_moviescatalog2023.Navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile_moviescatalog2023.View.IntroducingScreen.IntroducingScreen
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginScreen
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginViewModel
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen.RegistrationPasswordScreen
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen.RegistrationScreen
import com.example.mobile_moviescatalog2023.View.MainScreen.MainScreen
import com.example.mobile_moviescatalog2023.View.SplashScreen.SplashScreen
import org.koin.androidx.compose.getViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreenDestination(navController)
        }

        composable(Screen.Main.route) {
            MainScreen(navController = navController)
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
}

sealed class Screen(val route: String){
    object Splash: Screen(route = "splash_screen")
    object Introducing: Screen(route = "introducing_screen")
    object Login: Screen(route = "login_screen")
    object Registration: Screen(route = "registration_screen")
    object RegPass: Screen(route = "reg_pass_screen")
    object Main: Screen(route = "main_screen")
}