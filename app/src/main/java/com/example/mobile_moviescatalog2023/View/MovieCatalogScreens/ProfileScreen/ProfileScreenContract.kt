package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen

import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.RegistrationContract
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class ProfileScreenContract {

    sealed class Event : ViewEvent {
        class SaveNameEvent(val name: String) : Event()

        class SaveGenderEvent(val gender: Int) : Event()

        class SaveEmailEvent(val email: String) : Event()

        class SaveBirthDateEvent(val birthDate: String) : Event()

        class SaveBirthDateWithFormatEvent(val birthDate: Long?) : Event()

        class SaveUserIconUrl(val userIconUrl: String) : Event()
    }

    data class State(
        val email: String,
        val userIconUrl: String,
        val name: String,
        val gender: Int,
        val birthDate: String
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
