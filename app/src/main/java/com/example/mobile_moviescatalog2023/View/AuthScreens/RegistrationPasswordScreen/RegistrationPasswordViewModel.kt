package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationPasswordViewModel(
    private val context: Context,
    private val authRepository: AuthRepository
): BaseViewModel<
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

    private fun loadRegisterRequestBody(registerRequestBody: RegisterRequestBody) {
        setState {
            copy(
                userName = registerRequestBody.userName,
                name = registerRequestBody.name,
                email = registerRequestBody.email,
                birthDate = registerRequestBody.birthDate,
                gender = registerRequestBody.gender,
            )
        }
    }

    override fun setInitialState() = RegistrationPasswordContract.State(
        password = "",
        repPassword = "",
        userName = "",
        name = "",
        email = "",
        birthDate = "",
        gender = 0,
        isSuccess = null
    )

    override fun handleEvents(event: RegistrationPasswordContract.Event) {
        when(event) {
            is RegistrationPasswordContract.Event.SavePasswordEvent -> savePassword(event.password)
            is RegistrationPasswordContract.Event.SaveRepeatedPasswordEvent -> saveRepeatedPassword(event.repPassword)
            is RegistrationPasswordContract.Event.SignUp -> signUp()
            is RegistrationPasswordContract.Event.LoadRegisterRequestBody -> loadRegisterRequestBody(event.registerRequestBody)
        }
    }

    private fun signUp() {
        val registrationBody = RegisterRequestBody(
            userName = state.value.userName,
            name = state.value.name,
            password = state.value.password,
            email = state.value.email,
            birthDate = state.value.birthDate,
            gender = state.value.gender
        )

        viewModelScope.launch(Dispatchers.IO) {
            authRepository.register(registrationBody)
                .collect { result ->
                    result.onSuccess {
                        TokenManager(context).saveToken(it.token)
                        setState { copy(isSuccess = true) }
                    }.onFailure {
                        setState { copy(isSuccess = false) }
                    }
                }
        }
    }
}