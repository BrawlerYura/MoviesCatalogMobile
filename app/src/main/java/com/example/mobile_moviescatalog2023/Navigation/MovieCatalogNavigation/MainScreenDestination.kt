package com.example.mobile_moviescatalog2023.Navigation.MovieCatalogNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.MainScreen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenViewModel
 
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreenDestination(
    navController: NavHostController
) {
    val viewModel = getViewModel<MainScreenViewModel>()
    MainScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is MainScreenContract.Effect.Navigation.ToFilm -> {
                    navController.navigate(Screen.Film.route + "/${navigationEffect.id}")
                }

                is MainScreenContract.Effect.Navigation.ToFavorite -> {
                    navController.navigate(Screen.Favorite.route)
                }

                is MainScreenContract.Effect.Navigation.ToProfile -> {
                    navController.navigate(Screen.Profile.route)
                }
            }
        }
    )
}