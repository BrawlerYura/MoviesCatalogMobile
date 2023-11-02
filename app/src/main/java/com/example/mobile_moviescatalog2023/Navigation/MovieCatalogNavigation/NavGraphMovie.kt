package com.example.mobile_moviescatalog2023.Navigation.MovieCatalogNavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract

fun NavGraphBuilder.NavGraphMovie(
    navController: NavHostController,
    onNavigationRequested: (navigationEffect: MovieNavigationContract.Effect.Navigation) -> Unit
) {
    navigation(
        startDestination = Screen.Main.route,
        route = "movie_catalog"
    ) {

        composable(Screen.Main.route) {
            MainScreenDestination(
                navController,
                onNavigationRequested
            )
        }

        composable(Screen.Favorite.route) {
            FavoriteScreenDestination(
                navController,
                onNavigationRequested
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreenDestination(
                navController,
                onNavigationRequested
            )
        }

        composable(
            route = Screen.Film.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {entry ->
            FilmScreenDestination(
                navController,
               entry.arguments?.getString("id") ?: ""
            )
        }
    }
}