package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationPasswordScreen

interface RegistrationPasswordEvent {
    class SavePasswordEvent(val password: String) : RegistrationPasswordEvent

    class SaveRepeatedPasswordEvent(val repeatedPassword: String) : RegistrationPasswordEvent
}