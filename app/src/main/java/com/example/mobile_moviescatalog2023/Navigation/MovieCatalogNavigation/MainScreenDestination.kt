package com.example.mobile_moviescatalog2023.Navigation.MovieCatalogNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreenDestination(
    navController: NavHostController,
    onBottomNavigationRequested: (navigationEffect: MovieNavigationContract.Effect.Navigation) -> Unit
) {
    val viewModel = getViewModel<MainScreenViewModel>()
    MainScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is MainScreenContract.Effect.Navigation.ToFilm -> {
                    navController.navigate(Screen.Film.route)
                }
            }
        },
        onBottomNavigationRequested
    )
}