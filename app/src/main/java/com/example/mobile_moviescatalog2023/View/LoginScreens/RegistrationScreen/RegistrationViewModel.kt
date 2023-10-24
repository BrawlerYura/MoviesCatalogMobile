package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen.LoginContract

class RegistrationViewModel: BaseViewModel<RegistrationContract.Event, RegistrationContract.State, RegistrationContract.Effect>() {
    private fun saveName(name: String) {
        setState { copy(name = name) }
    }

    private fun saveGender(gender: String) {
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

    override fun setInitialState() = RegistrationContract.State (
        name = "",
        gender = "male",
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
        }
    }
}