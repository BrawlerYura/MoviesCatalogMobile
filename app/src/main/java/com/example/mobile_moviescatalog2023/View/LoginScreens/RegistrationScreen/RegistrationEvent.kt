package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen

interface RegistrationEvent {
    class SaveNameEvent(val name: String) : RegistrationEvent

    class SaveGenderEvent(val gender: String) : RegistrationEvent

    class SaveEmailEvent(val email: String) : RegistrationEvent

    class SaveLoginEvent(val login: String) : RegistrationEvent

    class SaveBirthDateEvent(val birthDate: String) : RegistrationEvent

    class SaveBirthDateWithFormatEvent(val birthDate: String) : RegistrationEvent

    class LoadNameEvent() : RegistrationEvent
}