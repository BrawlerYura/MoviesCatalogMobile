package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieDetailsModel
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class FilmScreenContract {

    sealed class Event : ViewEvent {
        class LoadFilmDetails(val id: String) : Event()
    }

    data class State(
        val isSuccess: Boolean?,
        val movieDetails: MovieDetailsModel?
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object Back : Navigation()
        }
    }
}