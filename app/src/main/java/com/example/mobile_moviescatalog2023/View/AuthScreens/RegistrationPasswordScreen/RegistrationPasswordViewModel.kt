package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationPasswordScreen

import android.content.Context
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.RegisterRequestBody
import com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases.RegisterUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.HandleErrorUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ValidationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RegistrationPasswordViewModel(
    private val context: Context,
    private val registerUseCase: RegisterUseCase,
    private val validationUseCase: ValidationUseCase,
    private val handleErrorUseCase: HandleErrorUseCase
) : BaseViewModel<
        RegistrationPasswordContract.Event,
        RegistrationPasswordContract.State,
        RegistrationPasswordContract.Effect
        >() {

    private fun savePassword(password: String) {
        setState { copy(password = password) }
        if (validationUseCase.checkIfPasswordValid(password)) {
            setState {
                copy(isPasswordValid = true)
            }
        } else {
            setState {
                copy(isPasswordValid = false)
            }
        }
    }

    private fun saveRepeatedPassword(repeatedPassword: String) {
        setState { copy(repPassword = repeatedPassword) }
        if (validationUseCase.checkIfPasswordValid(repeatedPassword)) {
            setState {
                copy(isRepPasswordValid = true)
            }
        } else {
            setState {
                copy(isRepPasswordValid = false)
            }
        }
    }

    override fun setInitialState() = RegistrationPasswordContract.State(
        password = "",
        repPassword = "",
        isSuccess = null,
        isPasswordValid = null,
        isRepPasswordValid = null,
        isError = false,
        errorMessage = null,
        isBodyLoaded = false,
        isLoading = false
    )

    override fun handleEvents(event: RegistrationPasswordContract.Event) {
        when (event) {
            is RegistrationPasswordContract.Event.SavePasswordEvent -> savePassword(event.password)
            is RegistrationPasswordContract.Event.SaveRepeatedPasswordEvent -> saveRepeatedPassword(
                event.repPassword
            )

            is RegistrationPasswordContract.Event.SignUp -> signUp(
                haptic = event.haptic,
                body = event.body
            )

            is RegistrationPasswordContract.Event.NavigationToLogin -> setEffect { RegistrationPasswordContract.Effect.Navigation.ToLogin }
            is RegistrationPasswordContract.Event.NavigationBack -> setEffect { RegistrationPasswordContract.Effect.Navigation.Back }
        }
    }

    private fun signUp(haptic: HapticFeedback, body: RegisterRequestBody) {
        setState { copy(isLoading = true) }
        if (!validationUseCase.checkIfPasswordEqualsRepeatedPassword(
                state.value.password,
                state.value.repPassword
            )
        ) {
            setState {
                copy(
                    isSuccess = false,
                    errorMessage = "Пароли не совпадают",
                    isLoading = false
                )
            }
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        } else {
            val registrationBody = RegisterRequestBody(
                userName = body.userName,
                name = body.name,
                password = state.value.password,
                email = body.email,
                birthDate = body.birthDate,
                gender = body.gender
            )
            viewModelScope.launch(Dispatchers.IO) {
                registerUseCase.invoke(registrationBody).collect { result ->
                    result.onSuccess {
                        TokenManager(context).saveToken(it.token)
                        Network.token = it.token
                        setState { copy(isSuccess = true, isError = false) }
                        MainScope().launch {
                            setEffect { RegistrationPasswordContract.Effect.Navigation.ToMain }
                        }
                    }.onFailure {
                        handleErrorUseCase.handleError(
                            error = it.message ?: "",
                            onTokenError = { },
                            onInputError = {
                                setState {
                                    copy(
                                        isSuccess = false,
                                        errorMessage = "Указаны некорректные данные",
                                        isLoading = false,
                                        isError = false
                                    )
                                }
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            onOtherError = {
                                setState { copy(isError = true, isLoading = false) }
                            }
                        )
                    }
                }
            }
        }
    }
}