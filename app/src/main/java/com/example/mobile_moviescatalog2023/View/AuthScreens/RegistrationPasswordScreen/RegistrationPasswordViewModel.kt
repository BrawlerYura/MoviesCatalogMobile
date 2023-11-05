package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen

import android.content.Context
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.R
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases.RegisterUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ValidationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationPasswordViewModel(
    private val context: Context,
    private val registerUseCase: RegisterUseCase,
    private val validationUseCase: ValidationUseCase
): BaseViewModel<
        RegistrationPasswordContract.Event,
        RegistrationPasswordContract.State,
        RegistrationPasswordContract.Effect
        >() {

    private fun savePassword(password: String) {
        setState { copy(password = password) }
        if(validationUseCase.checkIfPasswordValid(password)) {
            setState {
                copy( isPasswordValid = true )
            }
        } else {
            setState {
                copy( isPasswordValid = false )
            }
        }
    }

    private fun saveRepeatedPassword(repeatedPassword: String) {
        setState { copy(repPassword = repeatedPassword) }
        if(validationUseCase.checkIfPasswordValid(repeatedPassword)) {
            setState {
                copy( isRepPasswordValid = true )
            }
        } else {
            setState {
                copy( isRepPasswordValid = false )
            }
        }
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
        isSuccess = null,
        isPasswordValid = null,
        isRepPasswordValid = null,
        errorMessage = null
    )

    override fun handleEvents(event: RegistrationPasswordContract.Event) {
        when(event) {
            is RegistrationPasswordContract.Event.SavePasswordEvent -> savePassword(event.password)
            is RegistrationPasswordContract.Event.SaveRepeatedPasswordEvent -> saveRepeatedPassword(event.repPassword)
            is RegistrationPasswordContract.Event.SignUp -> signUp(haptic = event.haptic)
            is RegistrationPasswordContract.Event.LoadRegisterRequestBody -> loadRegisterRequestBody(event.registerRequestBody)
        }
    }

    private fun signUp(haptic: HapticFeedback) {
        if(!validationUseCase.checkIfPasswordEqualsRepeatedPassword(state.value.password, state.value.repPassword)) {
            setState { copy(isSuccess = false, errorMessage = "Пароли не совпадают") }
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        } else {
            val registrationBody = RegisterRequestBody(
                userName = state.value.userName,
                name = state.value.name,
                password = state.value.password,
                email = state.value.email,
                birthDate = state.value.birthDate,
                gender = state.value.gender
            )

            viewModelScope.launch(Dispatchers.IO) {
                registerUseCase.invoke(registrationBody)
                    .collect { result ->
                        result.onSuccess {
                            TokenManager(context).saveToken(it.token)
                            setState { copy(isSuccess = true) }
                        }.onFailure {
                            setState { copy(isSuccess = false, errorMessage = "Указаны некорректные данные") }
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                    }
            }
        }
    }
}