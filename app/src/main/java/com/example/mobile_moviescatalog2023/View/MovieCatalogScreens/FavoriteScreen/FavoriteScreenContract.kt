package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen

import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState
import com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen.MainScreenContract
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ThreeFavoriteMovies

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
            class ToFilm(val id: String) : Navigation()
        }
    }
}