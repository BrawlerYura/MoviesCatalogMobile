package com.example.mobile_moviescatalog2023.Navigation

import androidx.compose.animation.AnimatedContentTransitionScope
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
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginScreen
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen.RegistrationPasswordScreen
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen.RegistrationScreen
import com.example.mobile_moviescatalog2023.View.MainScreen.MainScreen
import com.example.mobile_moviescatalog2023.View.SplashScreen.SplashScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()

    val noEnterTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        fadeIn(
            animationSpec = tween(durationMillis = 0)
        )
    }

    val noExitTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        fadeOut(
            animationSpec = tween(durationMillis = 0)
        )
    }

    NavHost(
        navController = navController,
        startDestination = NavigationModel.MainScreens.SplashScreen.name
    ) {
        composable(NavigationModel.MainScreens.SplashScreen.name, enterTransition = noEnterTransition, exitTransition = noExitTransition) {
            SplashScreen(navController = navController)
        }

        composable(NavigationModel.MainScreens.MainScreen.name, enterTransition = noEnterTransition, exitTransition = noExitTransition) {
            MainScreen(navController = navController)
        }

        composable(NavigationModel.MainScreens.IntroducingScreen.name, enterTransition = noEnterTransition, exitTransition = noExitTransition) {
            IntroducingScreen(navController = navController)
        }

        composable(NavigationModel.MainScreens.LoginScreen.name, enterTransition = noEnterTransition, exitTransition = noExitTransition) {
            LoginScreen(navController = navController)
        }

        composable(NavigationModel.MainScreens.RegistrationScreen.name, enterTransition = noEnterTransition, exitTransition = noExitTransition) {
            RegistrationScreen(navController = navController)
        }

        composable(NavigationModel.MainScreens.RegistrationPasswordScreen.name, enterTransition = noEnterTransition, exitTransition = noExitTransition) {
            RegistrationPasswordScreen(navController = navController)
        }
    }
}

class NavigationModel : ViewModel() {
    enum class MainScreens {
        SplashScreen,
        MainScreen,
        IntroducingScreen,
        LoginScreen,
        RegistrationScreen,
        RegistrationPasswordScreen;
    }
}
