package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen

import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel

class ProfileScreenViewModel(
): BaseViewModel<ProfileScreenContract.Event, ProfileScreenContract.State, ProfileScreenContract.Effect>() {

    override fun setInitialState() = ProfileScreenContract.State(
        email = "",
        userIconUrl = "",
        name = "",
        gender = 0,
        birthDate = ""
    )

    override fun handleEvents(event: ProfileScreenContract.Event) {
        when (event) {
            is ProfileScreenContract.Event.SaveEmailEvent -> saveEmail(email = event.email)
            is ProfileScreenContract.Event.SaveUserIconUrl -> saveUserIconUrl(userIconUrl = event.userIconUrl)
            is ProfileScreenContract.Event.SaveNameEvent -> saveName(name = event.name)
            is ProfileScreenContract.Event.SaveGenderEvent -> saveGender(gender = event.gender)
            is ProfileScreenContract.Event.SaveBirthDateEvent -> saveBirthDate(birthDate = event.birthDate)
            is ProfileScreenContract.Event.SaveBirthDateWithFormatEvent -> saveBirthDate(formatDateToTextField(event.birthDate))
        }
    }

    private fun formatDateToApi(date: String): String {
        val parts = date.split(".")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        return "$year-$month-${day}T08:12:28.534Z"
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

    private fun saveUserIconUrl(userIconUrl: String) {
        setState { copy(userIconUrl = userIconUrl) }
    }

    private fun saveBirthDate(birthDate: String) {
        setState { copy(birthDate = birthDate) }
    }

    private fun formatDateToTextField(date: String): String {
        val parts = date.split("-")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        return "$day.$month.$year"
    }
}