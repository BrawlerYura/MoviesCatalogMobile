package com.example.mobile_moviescatalog2023.Navigation.MovieCatalogNavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.mobile_moviescatalog2023.Navigation.Screen


fun NavGraphBuilder.NavGraphMovie(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Main.route,
        route = "movie_catalog"
    ) {

        composable(Screen.Main.route) {
            MainScreenDestination(
                navController
            )
        }

        composable(Screen.Favorite.route) {
            FavoriteScreenDestination(
                navController
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreenDestination(
                navController
            )
        }

        composable(
            route = Screen.Film.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { entry ->
            FilmScreenDestination(
                navController,
                entry.arguments?.getString("id") ?: ""
            )
        }
    }
}