package com.example.mobile_moviescatalog2023.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mobile_moviescatalog2023.Navigation.AuthNavigation.NavGraphAuth
import com.example.mobile_moviescatalog2023.Navigation.MovieCatalogNavigation.NavGraphMovie
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import org.koin.androidx.compose.getViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        route = "root"
    )
    {
        NavGraphAuth(navController)
        NavGraphMovie(
            navController,
            onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is MovieNavigationContract.Effect.Navigation.ToFilm -> { }

                is MovieNavigationContract.Effect.Navigation.ToFavorite -> {
                    navController.navigate(Screen.Favorite.route)
                }

                is MovieNavigationContract.Effect.Navigation.ToProfile -> {
                    navController.navigate(Screen.Profile.route)
                }

                is MovieNavigationContract.Effect.Navigation.ToMain -> {
                    navController.navigate(Screen.Main.route)
                }
            }
        }
        )
    }
}

sealed class Screen(val route: String){
    object Splash: Screen(route = "splash_screen")
    object Introducing: Screen(route = "introducing_screen")
    object Login: Screen(route = "login_screen")
    object Registration: Screen(route = "registration_screen")
    object RegPass: Screen(route = "reg_pass_screen")
    object Main: Screen(route = "main_screen")
    object Favorite: Screen(route = "favorite_screen")
    object Profile: Screen(route = "profile_screen")
}