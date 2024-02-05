package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen

import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ThreeFavoriteMovies

class FavoriteScreenContract {

    sealed class Event : ViewEvent {
        object GetFavoriteMovies : Event()
        object RefreshScreen : Event()
        object NavigationToMain : Event()
        object NavigationToProfile : Event()
        class NavigationToFilm(val id: String) : Event()
    }

    data class State(
        val favoriteMovieList: List<ThreeFavoriteMovies>?,
        val isLoaded: Boolean,
        val isError: Boolean,
        val isRefreshing: Boolean,
        val myRating: List<FilmRating?>,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            class ToFilm(val id: String) : Navigation()
            object ToMain : Navigation()
            object ToProfile : Navigation()
            object ToIntroducing : Navigation()
        }
    }
}