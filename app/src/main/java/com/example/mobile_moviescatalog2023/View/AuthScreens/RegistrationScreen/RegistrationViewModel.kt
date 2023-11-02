package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen

import android.annotation.SuppressLint
import com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class RegistrationViewModel(

): BaseViewModel<RegistrationContract.Event, RegistrationContract.State, RegistrationContract.Effect>() {

    private fun formatDateToApi(date: String): String {
        val parts = date.split(".")
        val year = parts[2]
        val month = parts[1]
        val day = parts[0]
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

    private fun saveLogin(login: String) {
        setState { copy(login = login) }
    }

    private fun saveBirthDate(birthDate: String) {
        setState { copy(birthDate = birthDate, apiBirthDate = formatDateToApi(birthDate)) }
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDateToTextField(selectedDateMillis: Long?): String {
        if (selectedDateMillis == null) {
            return ""
        }

        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = Date(selectedDateMillis)
        return dateFormat.format(date)
    }

    override fun setInitialState() = RegistrationContract.State(
        name = "",
        gender = 0,
        login = "",
        email = "",
        birthDate = "",
        apiBirthDate = ""
    )

    override fun handleEvents(event: RegistrationContract.Event) {
        when (event) {
            is RegistrationContract.Event.SaveNameEvent -> saveName(event.name)
            is RegistrationContract.Event.SaveGenderEvent -> saveGender(event.gender)
            is RegistrationContract.Event.SaveEmailEvent -> saveEmail(event.email)
            is RegistrationContract.Event.SaveLoginEvent -> saveLogin(event.login)
            is RegistrationContract.Event.SaveBirthDateEvent -> saveBirthDate(event.birthDate)
            is RegistrationContract.Event.SaveBirthDateWithFormatEvent -> saveBirthDate(formatDateToTextField(event.birthDate))
        }
    }
}