package com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen

import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class LoginContract {

    sealed class Event : ViewEvent {
        class SaveLoginEvent(val login: String) : Event()

        class SavePasswordEvent(val password: String) : Event()
    }

    data class State(
        val login: String,
        val password: String
    ) : ViewState

    sealed class Effect : ViewSideEffect{
        sealed class Navigation : Effect() {
            object SignUp : Navigation()
            object SignIn : Navigation()
            object Back : Navigation()
        }
    }

}