package com.example.mobile_moviescatalog2023.Navigation.MovieCatalogNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.Composables.FavoriteScreen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import org.koin.androidx.compose.getViewModel

@Composable
fun FavoriteScreenDestination(
    navController: NavHostController
    ) {
    val viewModel = getViewModel<FavoriteScreenViewModel>()
    FavoriteScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is FavoriteScreenContract.Effect.Navigation.ToFilm -> {
                    navController.navigate(Screen.Film.route + "/${navigationEffect.id}")
                }

                is FavoriteScreenContract.Effect.Navigation.ToProfile -> {
                    navController.navigate(Screen.Profile.route){
                        popUpTo(Screen.Profile.route)
                        launchSingleTop = true
                    }
                }

                is FavoriteScreenContract.Effect.Navigation.ToMain -> {
                    navController.navigate(Screen.Main.route){
                        popUpTo(Screen.Main.route)
                        launchSingleTop = true
                    }
                }
                is FavoriteScreenContract.Effect.Navigation.ToIntroducing -> {
                    navController.navigate(Screen.Introducing.route) {
                        popUpTo(Screen.Main.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    )
}