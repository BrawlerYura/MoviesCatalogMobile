package com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen

import android.content.Context
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class LoginContract {

    sealed class Event : ViewEvent {
        class SaveLoginEvent(val login: String) : Event()

        class SavePasswordEvent(val password: String) : Event()

        class SignIn(val login: String, val password: String, val context: Context) : Event()
    }

    data class State(
        val login: String,
        val password: String,
        val isTriedToSignIn: Boolean,
        val isSuccess: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect{
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToRegistration : Navigation()
            object Back : Navigation()
        }
    }
}