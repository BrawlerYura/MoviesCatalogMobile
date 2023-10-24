package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationPasswordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel

class RegistrationPasswordViewModel: BaseViewModel<
        RegistrationPasswordContract.Event,
        RegistrationPasswordContract.State,
        RegistrationPasswordContract.Effect
        >() {

    private fun savePassword(password: String) {
        setState { copy(password = password) }
    }

    private fun saveRepeatedPassword(repeatedPassword: String) {
        setState { copy(repPassword = repeatedPassword) }
    }

    override fun setInitialState() = RegistrationPasswordContract.State (
        password = "",
        repPassword = ""
    )

    override fun handleEvents(event: RegistrationPasswordContract.Event) {
        when(event) {
            is RegistrationPasswordContract.Event.SavePasswordEvent -> savePassword(event.password)
            is RegistrationPasswordContract.Event.SaveRepeatedPasswordEvent -> saveRepeatedPassword(event.repPassword)
        }
    }
}