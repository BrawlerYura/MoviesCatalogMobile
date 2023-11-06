package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen

import coil.compose.AsyncImagePainter
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.Composables.FilmRating

class MainScreenContract {

    sealed class Event : ViewEvent {
        object UpdateMoviesList : Event()
        object GetMovies : Event()
        class NavigationToFilm(val id: String) : Event()
        object NavigationToProfile : Event()
        object NavigationToFavorite : Event()
    }

    data class State(
        val movieList: List<MovieElementModel>,
        val movieCarouselList: List<MovieElementModel>,
        val filmRatingsList: List<FilmRating?>,
        val currentMoviePage: Int,
        val isRequestingMoviePage: Boolean,
        val isSuccess: Boolean,
        val pageCount: Int,
        val isUpdatingList: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            class ToFilm(val id: String) : Navigation()
            object ToProfile : Navigation()
            object ToFavorite : Navigation()
        }
    }
}