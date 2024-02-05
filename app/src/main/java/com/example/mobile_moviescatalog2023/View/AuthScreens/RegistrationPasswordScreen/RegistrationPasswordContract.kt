package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen

import androidx.compose.ui.hapticfeedback.HapticFeedback
import com.example.mobile_moviescatalog2023.View.Base.ViewEvent
import com.example.mobile_moviescatalog2023.View.Base.ViewSideEffect
import com.example.mobile_moviescatalog2023.View.Base.ViewState
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody

class RegistrationPasswordContract {

    sealed class Event : ViewEvent {
        class SavePasswordEvent(val password: String) : Event()
        class SaveRepeatedPasswordEvent(val repPassword: String) : Event()
        class SignUp(val haptic: HapticFeedback, val body: RegisterRequestBody) : Event()
        object NavigationToLogin : Event()
        object NavigationBack : Event()
    }

    data class State(
        val password: String,
        val repPassword: String,
        val isSuccess: Boolean?,
        val isPasswordValid: Boolean?,
        val isRepPasswordValid: Boolean?,
        val errorMessage: String?,
        val isBodyLoaded: Boolean,
        val isLoading: Boolean,
        val isError: Boolean
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
            object ToLogin : Navigation()
            object Back : Navigation()
        }
    }
}