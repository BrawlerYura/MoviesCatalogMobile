package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen

import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class FavoriteScreenContract {

    sealed class Event : ViewEvent {

    }

    data class State(
        val temp: String
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