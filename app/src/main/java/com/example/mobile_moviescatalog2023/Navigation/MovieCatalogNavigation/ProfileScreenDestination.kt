package com.example.mobile_moviescatalog2023.Navigation.MovieCatalogNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_moviescatalog2023.Navigation.Screen
import com.example.mobile_moviescatalog2023.Navigation.Screen.Favorite.route
import com.example.mobile_moviescatalog2023.Navigation.Screen.Login.route
import com.example.mobile_moviescatalog2023.Navigation.Screen.Splash.route
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract

import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.Composables.ProfileScreen
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenContract
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen.ProfileScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreenDestination(
    navController: NavHostController
) {
    val viewModel = getViewModel<ProfileScreenViewModel>()
    ProfileScreen(
        state = viewModel.state.value,
        onEventSent = { event -> viewModel.setEvent(event) },
        effectFlow = viewModel.effect,
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is ProfileScreenContract.Effect.Navigation.ToFavorite -> {
                    navController.navigate(Screen.Favorite.route) {
                        popUpTo(Screen.Favorite.route)
                        launchSingleTop = true
                    }
                }

                is ProfileScreenContract.Effect.Navigation.ToMain -> {
                    navController.navigate(Screen.Main.route){
                        popUpTo(Screen.Main.route)
                        launchSingleTop = true
                    }
                }

                is ProfileScreenContract.Effect.Navigation.ToIntroducing -> {
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