package com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen

import android.content.Context
import androidx.compose.ui.hapticfeedback.HapticFeedback
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState

class LoginContract {

    sealed class Event : ViewEvent {
        class SaveLoginEvent(val login: String) : Event()

        class SavePasswordEvent(val password: String) : Event()

        class SignIn(val haptic: HapticFeedback) : Event()
        object NavigationToRegistration : Event()
        object NavigationBack : Event()
    }

    data class State(
        val login: String,
        val password: String,
        val isSuccess: Boolean?,
        val buttonEnabled: Boolean,
        val errorMessage: String?
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToRegistration : Navigation()
            object Back : Navigation()
        }
    }
}