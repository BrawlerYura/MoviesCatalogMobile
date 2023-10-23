package com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationPasswordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationPasswordViewModel: ViewModel() {

    private val stateLiveMutable = MutableLiveData<RegistrationPasswordState>()
    val stateLive: LiveData<RegistrationPasswordState> = stateLiveMutable

    init {
        stateLiveMutable.value = RegistrationPasswordState("", "")
    }

    fun send(event: RegistrationPasswordEvent) {
        when(event) {
            is RegistrationPasswordEvent.SavePasswordEvent -> {
                savePassword(event.password)
            }
            is RegistrationPasswordEvent.SaveRepeatedPasswordEvent -> {
                saveRepeatedPassword(event.repeatedPassword)
            }
        }
    }

    private fun savePassword(password: String) {
        stateLiveMutable.value = stateLiveMutable.value?.copy(password = password)
    }

    private fun saveRepeatedPassword(repeatedPassword: String) {
        stateLiveMutable.value = stateLiveMutable.value?.copy(repeatedPassword = repeatedPassword)
    }
}