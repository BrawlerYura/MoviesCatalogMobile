package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen

import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract

class FavoriteScreenContract {

    sealed class Event : ViewEvent {
        object GetFavoriteMovies: Event()
    }

    data class State(
        val favoriteMovieList: List<ThreeFavoriteMovies>?,
        val isSuccess: Boolean?,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToFilm : Navigation()
            object ToFavorite : Navigation()
            object ToProfile : Navigation()
        }
    }
}