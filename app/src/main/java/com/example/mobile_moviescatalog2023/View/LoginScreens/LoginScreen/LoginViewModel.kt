package com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_moviescatalog2023.View.LoginScreens.RegistrationScreen.RegistrationEvent

class LoginViewModel: ViewModel() {
    private val stateLiveMutable = MutableLiveData<LoginState>()
    val stateLive: LiveData<LoginState> = stateLiveMutable

    init {
        stateLiveMutable.value = LoginState("", "")
    }

    fun send(event: LoginEvent) {
        when(event) {
            is LoginEvent.SaveLoginEvent -> {
                saveLogin(event.login)
            }
            is LoginEvent.SavePasswordEvent -> {
                savePassword(event.password)
            }
        }
    }

    private fun saveLogin(login: String) {
        stateLiveMutable.value = stateLiveMutable.value?.copy(login = login)
    }

    private fun savePassword(password: String) {
        stateLiveMutable.value = stateLiveMutable.value?.copy(password = password)
    }
}