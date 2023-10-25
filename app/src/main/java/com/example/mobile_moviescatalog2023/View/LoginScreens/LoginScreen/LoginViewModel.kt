package com.example.mobile_moviescatalog2023.View.LoginScreens.LoginScreen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies.LoginRequestBody
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val authRepository = AuthRepository()
class LoginViewModel: BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun setInitialState() = LoginContract.State (
        login = "",
        password = "",
        isTriedToSignIn = false,
        isSuccess = false
    )

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.SaveLoginEvent -> saveLogin(login = event.login)
            is LoginContract.Event.SavePasswordEvent -> savePassword(password = event.password)
            is LoginContract.Event.SignIn -> signIn(login = event.login, password = event.password, context = event.context)
        }
    }

    private fun saveLogin(login: String) {
        setState { copy(login = login) }
    }

    private fun savePassword(password: String) {
        setState { copy(password = password) }
    }

    private fun signIn(login: String, password: String, context: Context) {
        val loginBody = LoginRequestBody(login, password)

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