package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen

import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class RegistrationContract {

    sealed class Event : ViewEvent {
        class SaveNameEvent(val name: String) : Event()

        class SaveGenderEvent(val gender: Int) : Event()

        class SaveEmailEvent(val email: String) : Event()

        class SaveLoginEvent(val login: String) : Event()

        class SaveBirthDateEvent(val birthDate: String) : Event()

        class SaveBirthDateWithFormatEvent(val birthDate: Long?) : Event()

        object TransferBody: Event()
    }

    data class State (
        val name: String,
        val gender: Int,
        val login: String,
        val email: String,
        val birthDate: String
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object NextScreen : Navigation()
            object ToLogin : Navigation()
            object Back : Navigation()
        }
    }
}