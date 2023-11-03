package com.example.mobile_moviescatalog2023.Navigation.MovieCatalogNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.Composables.FilmScreen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen.FilmScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun FilmScreenDestination(
    navController: NavHostController,
    id: String
    ) {
    val viewModel = getViewModel<FilmScreenViewModel>()
    FilmScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is FilmScreenContract.Effect.Navigation.Back -> {
                    navController.navigateUp()
                }
            }
        },
        filmId = id
    )
}