package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen

import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody

class RegistrationContract {

    sealed class Event : ViewEvent {
        class SaveNameEvent(val name: String) : Event()

        class SaveGenderEvent(val gender: Int) : Event()

        class SaveEmailEvent(val email: String) : Event()

        class SaveLoginEvent(val login: String) : Event()

        class SaveBirthDateEvent(val birthDate: String) : Event()

        class SaveBirthDateWithFormatEvent(val birthDate: Long?) : Event()
    }

    data class State(
        val name: String,
        val gender: Int,
        val login: String,
        val email: String,
        val birthDate: String,
        val apiBirthDate: String,
        val isNameValid: Boolean?,
        val isLoginValid: Boolean?,
        val isEmailValid: Boolean?,
        val isBirthDateValid: Boolean?
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            class NextScreen(val registerRequestBody: RegisterRequestBody) : Navigation()
            object ToLogin : Navigation()
            object Back : Navigation()
        }
    }
}