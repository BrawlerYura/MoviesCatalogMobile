package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen

import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class RegistrationPasswordContract {

    sealed class Event : ViewEvent {
        class SavePasswordEvent(val password: String) : Event()
        class SaveRepeatedPasswordEvent(val repPassword: String) : Event()
        object SignUp : Event()
        class LoadRegisterRequestBody(val registerRequestBody: RegisterRequestBody) : Event()
    }

    data class State(
        val password: String,
        val repPassword: String,
        val userName: String,
        val name: String,
        val email: String,
        val birthDate: String,
        val gender: Int,
        val isSuccess: Boolean?
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToLogin : Navigation()
            object Back : Navigation()
        }
    }
}