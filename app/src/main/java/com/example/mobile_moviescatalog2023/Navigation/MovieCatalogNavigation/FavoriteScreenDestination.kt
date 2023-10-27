package com.example.mobile_moviescatalog2023.Navigation.MovieCatalogNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import org.koin.androidx.compose.getViewModel

@Composable
fun FavoriteScreenDestination(
    navController: NavHostController,
    onBottomNavigationRequested: (navigationEffect: MovieNavigationContract.Effect.Navigation) -> Unit
    ) {
    val viewModel = getViewModel<FavoriteScreenViewModel>()
    FavoriteScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is FavoriteScreenContract.Effect.Navigation.ToFilm -> { }

                is FavoriteScreenContract.Effect.Navigation.ToFavorite -> {
                    navController.navigate(Screen.Favorite.route)
                }

                is FavoriteScreenContract.Effect.Navigation.ToProfile -> {
                    navController.navigate(Screen.Profile.route)
                }

                is FavoriteScreenContract.Effect.Navigation.ToMain -> {
                    navController.navigate(Screen.Main.route)
                }
            }
        },
        onBottomNavigationRequested
    )
}