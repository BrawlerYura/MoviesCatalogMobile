package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen

import android.annotation.SuppressLint
import android.util.Log
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.FormatDateUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ValidationUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class RegistrationViewModel(
    private val formatDateUseCase: FormatDateUseCase,
    private val validationUseCase: ValidationUseCase
): BaseViewModel<RegistrationContract.Event, RegistrationContract.State, RegistrationContract.Effect>() {
    private fun saveName(name: String) {
        setState { copy(name = name) }
        if(validationUseCase.checkIfNameValid(state.value.name)){
            setState {
                copy(isNameValid = true)
            }
        } else {
            setState {
                copy(isNameValid = false)
            }
        }
    }

    private fun saveGender(gender: Int) {
        setState { copy(gender = gender) }
    }

    private fun saveEmail(email: String) {
        setState { copy(email = email) }
        if(validationUseCase.checkIfEmailValid(state.value.email)){
            setState {
                copy(isEmailValid = true)
            }
        } else {
            setState {
                copy(isEmailValid = false)
            }
        }
    }

    private fun saveLogin(login: String) {
        setState { copy(login = login) }
        if(validationUseCase.checkIfLoginValid(state.value.login)){
            setState {
                copy(isLoginValid = true)
            }
        } else {
            setState {
                copy(isLoginValid = false)
            }
        }
    }

    private fun saveBirthDate(birthDate: String) {
        setState {
            copy(
                birthDate = birthDate,
                apiBirthDate = try {
                    formatDateUseCase.formatDateToApi(birthDate)
                } catch(e: Throwable) {
                    ""
                }
            )
        }
        if(validationUseCase.checkIfBirthDateValid(state.value.birthDate)){
            setState {
                copy(isBirthDateValid = true)
            }
        } else {
            setState {
                copy(isBirthDateValid = false)
            }
        }
    }

    override fun setInitialState() = RegistrationContract.State(
        name = "",
        gender = 0,
        login = "",
        email = "",
        birthDate = "",
        apiBirthDate = "",
        isNameValid = null,
        isLoginValid = null,
        isEmailValid = null,
        isBirthDateValid = null
    )

    override fun handleEvents(event: RegistrationContract.Event) {
        when (event) {
            is RegistrationContract.Event.SaveNameEvent -> saveName(event.name)
            is RegistrationContract.Event.SaveGenderEvent -> saveGender(event.gender)
            is RegistrationContract.Event.SaveEmailEvent -> saveEmail(event.email)
            is RegistrationContract.Event.SaveLoginEvent -> saveLogin(event.login)
            is RegistrationContract.Event.SaveBirthDateEvent -> saveBirthDate(event.birthDate)
            is RegistrationContract.Event.SaveBirthDateWithFormatEvent -> saveBirthDate(formatDateUseCase.formatDateToTextField(event.birthDate))
        }
    }
}