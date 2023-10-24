package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationPasswordScreen

import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class RegistrationPasswordContract {

    sealed class Event : ViewEvent {
        class SavePasswordEvent(val password: String) : Event()

        class SaveRepeatedPasswordEvent(val repPassword: String) : Event()
    }

    data class State(
        val password: String,
        val repPassword: String
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToLogin : Navigation()
            object Back : Navigation()
        }
    }
}