package com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen

import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel

class LoginViewModel () : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    private fun saveLogin(login: String) {
        setState { copy(login = login) }
    }

    private fun savePassword(password: String) {
        setState { copy(password = password) }
    }

    override fun setInitialState() = LoginContract.State (
        login = "",
        password = ""
    )

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.SaveLoginEvent -> saveLogin(login = event.login)
            is LoginContract.Event.SavePasswordEvent -> savePassword(password = event.password)
        }
    }
}