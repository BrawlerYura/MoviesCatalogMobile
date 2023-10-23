package com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen

interface LoginEvent {
    class SaveLoginEvent(val login: String) : LoginEvent

    class SavePasswordEvent(val password: String) : LoginEvent
}