package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen

import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel

class MainScreenContract {

    sealed class Event : ViewEvent {
        object UpdateMoviesList : Event()
        object GetMovies : Event()
        object RefreshMovies : Event()
        class NavigationToFilm(val id: String) : Event()
        object NavigationToProfile : Event()
        object NavigationToFavorite : Event()
    }

    data class State(
        val movieList: List<MovieElementModel>,
        val filmRatingsList: List<FilmRating?>,
        val currentMoviePage: Int,
        val isError: Boolean,
        val isLoaded: Boolean,
        val pageCount: Int,
        val isUpdatingList: Boolean,
        val myRating: List<FilmRating?>,
        val isRefreshing: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            class ToFilm(val id: String) : Navigation()
            object ToProfile : Navigation()
            object ToFavorite : Navigation()
            object ToIntroducing : Navigation()
        }
    }
}