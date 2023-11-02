package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen

import coil.compose.AsyncImagePainter
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MoviesModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.ReviewShortModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class MainScreenContract {

    sealed class Event : ViewEvent {
        object UpdateMoviesList : Event()
        object GetMovies : Event()
        class CalculateFilmScore(val reviews: List<ReviewShortModel>?) : Event()
    }

    data class State(
        val movieList: List<MovieElementModel>,
        val movieCarouselList: List<MovieElementModel>,
        val currentMoviePage: Int,
        val isRequestingMoviePage: Boolean,
        val isSuccess: Boolean,
        val pageCount: Int,
        val isUpdatingList: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            class ToFilm(val id: String) : Navigation()
        }
    }
}

class MovieNavigationContract {
    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToFavorite : Navigation()
            object ToProfile : Navigation()
        }
    }
}