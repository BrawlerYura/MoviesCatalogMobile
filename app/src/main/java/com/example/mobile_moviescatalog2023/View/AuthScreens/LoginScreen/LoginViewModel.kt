package com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies.LoginRequestBody
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val context: Context
): BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    private val authRepository = AuthRepository()

    override fun setInitialState() = LoginContract.State(
        login = "",
        password = "",
        isTriedToSignIn = false,
        isSuccess = false
    )

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.SaveLoginEvent -> saveLogin(login = event.login)
            is LoginContract.Event.SavePasswordEvent -> savePassword(password = event.password)
            is LoginContract.Event.SignIn -> signIn()
        }
    }

    private fun saveLogin(login: String) {
        setState { copy(login = login) }
    }

    private fun savePassword(password: String) {
        setState { copy(password = password) }
    }

    private fun signIn() {
        val loginBody = LoginRequestBody(state.value.login, state.value.password)

        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(loginBody)
                .collect { result ->
                    result.onSuccess {
                        TokenManager(context).saveToken(it.token)
                        setState { copy(isTriedToSignIn = true, isSuccess = true) }
                    }.onFailure {
                        setState { copy(isTriedToSignIn = true, isSuccess = false) }
                    }
                }
        }
    }
}