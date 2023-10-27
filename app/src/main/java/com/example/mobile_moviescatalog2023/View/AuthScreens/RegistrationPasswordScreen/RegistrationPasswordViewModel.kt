package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.Network.DataClasses.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen.SharedRegisterDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationPasswordViewModel(
    private val context: Context
): BaseViewModel<
        RegistrationPasswordContract.Event,
        RegistrationPasswordContract.State,
        RegistrationPasswordContract.Effect
        >() {


    private val authRepository = AuthRepository()

    private fun savePassword(password: String) {
        setState { copy(password = password) }
    }

    private fun saveRepeatedPassword(repeatedPassword: String) {
        setState { copy(repPassword = repeatedPassword) }
    }

    override fun setInitialState() = RegistrationPasswordContract.State(
        password = "",
        repPassword = "",
        userName = SharedRegisterDataService.sharedData.userName,
        name = SharedRegisterDataService.sharedData.name,
        email = SharedRegisterDataService.sharedData.email,
        birthDate = SharedRegisterDataService.sharedData.birthDate,
        gender = SharedRegisterDataService.sharedData.gender,
        isSuccess = null
    )

    override fun handleEvents(event: RegistrationPasswordContract.Event) {
        when(event) {
            is RegistrationPasswordContract.Event.SavePasswordEvent -> savePassword(event.password)
            is RegistrationPasswordContract.Event.SaveRepeatedPasswordEvent -> saveRepeatedPassword(event.repPassword)
            is RegistrationPasswordContract.Event.SignUp -> signUp()
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