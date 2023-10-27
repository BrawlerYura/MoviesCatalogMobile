package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen

import com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel

class SharedRegisterDataService {
    companion object {
        lateinit var sharedData: RegisterRequestBody
    }
}

class RegistrationViewModel: BaseViewModel<RegistrationContract.Event, RegistrationContract.State, RegistrationContract.Effect>() {



    private fun transferBody() {
        SharedRegisterDataService.sharedData = RegisterRequestBody(
            userName = state.value.login,
            name = state.value.name,
            password = "",
            email = state.value.email,
            birthDate = state.value.birthDate,
            gender = state.value.gender
        )
    }
    private fun saveName(name: String) {
        setState { copy(name = name) }
    }

    private fun saveGender(gender: Int) {
        setState { copy(gender = gender) }
    }

    private fun saveEmail(email: String) {
        setState { copy(email = email) }
    }

    private fun saveLogin(login: String) {
        setState { copy(login = login) }
    }

    private fun saveBirthDate(birthDate: String) {
        setState { copy(birthDate = birthDate) }
    }

    private fun formatDate(date: String): String {
        val parts = date.split("-")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        return "$day.$month.$year"
    }

    override fun setInitialState() = RegistrationContract.State(
        name = "",
        gender = 0,
        login = "",
        email = "",
        birthDate = ""
    )

    override fun handleEvents(event: RegistrationContract.Event) {
        when (event) {
            is RegistrationContract.Event.SaveNameEvent -> saveName(event.name)
            is RegistrationContract.Event.SaveGenderEvent -> saveGender(event.gender)
            is RegistrationContract.Event.SaveEmailEvent -> saveEmail(event.email)
            is RegistrationContract.Event.SaveLoginEvent -> saveLogin(event.login)
            is RegistrationContract.Event.SaveBirthDateEvent -> saveBirthDate(event.birthDate)
            is RegistrationContract.Event.SaveBirthDateWithFormatEvent -> saveBirthDate(formatDate(event.birthDate))
            is RegistrationContract.Event.TransferBody -> transferBody()
        }
    }
}