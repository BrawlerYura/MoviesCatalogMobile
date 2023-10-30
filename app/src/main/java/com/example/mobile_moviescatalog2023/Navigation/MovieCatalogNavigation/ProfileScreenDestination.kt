package com.example.mobile_moviescatalog2023.Navigation.MovieCatalogNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen.FavoriteScreenViewModel
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MovieNavigationContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreenDestination(
    navController: NavHostController,
    onBottomNavigationRequested: (navigationEffect: MovieNavigationContract.Effect.Navigation) -> Unit
    ) {
    val viewModel = getViewModel<ProfileScreenViewModel>()
    ProfileScreen(
        state = viewModel.state.value,
        onEventSent = { event ->  viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is ProfileScreenContract.Effect.Navigation.ToFilm -> { }

                is ProfileScreenContract.Effect.Navigation.ToFavorite -> {
                    navController.navigate(Screen.Favorite.route)
                }

                is ProfileScreenContract.Effect.Navigation.ToProfile -> {
                    navController.navigate(Screen.Profile.route)
                }

                is ProfileScreenContract.Effect.Navigation.ToIntroducing -> {
                    navController.navigate(Screen.Introducing.route) {
                        popUpTo(Screen.Main.route) {
                            inclusive = true
                        }
                    }
                }
            }
        },
        onBottomNavigationRequested
    )
}