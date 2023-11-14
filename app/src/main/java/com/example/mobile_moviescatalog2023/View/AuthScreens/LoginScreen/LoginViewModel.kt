package com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen

import android.content.Context
import android.util.Log
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.LoginRequestBody
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases.LoginUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.HandleErrorUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ValidationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val context: Context,
    private val loginUseCase: LoginUseCase,
    private val validationUseCase: ValidationUseCase,
    private val handleErrorUseCase: HandleErrorUseCase
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun setInitialState() = LoginContract.State(
        login = "",
        password = "",
        isSuccess = null,
        buttonEnabled = false,
        errorMessage = null,
        isLoading = false,
        isError = false
    )

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.SaveLoginEvent -> saveLogin(login = event.login)
            is LoginContract.Event.SavePasswordEvent -> savePassword(password = event.password)
            is LoginContract.Event.SignIn -> signIn(haptic = event.haptic)
            is LoginContract.Event.NavigationToRegistration -> setEffect { LoginContract.Effect.Navigation.ToRegistration }
            is LoginContract.Event.NavigationBack -> setEffect { LoginContract.Effect.Navigation.Back }
        }
    }

    private fun saveLogin(login: String) {
        setState { copy(login = login) }
        checkIfTextBoxesValid()
    }

    private fun savePassword(password: String) {
        setState { copy(password = password) }
        checkIfTextBoxesValid()
    }

    private fun checkIfTextBoxesValid() {
        if (validationUseCase.checkIfLoginValid(state.value.login)
            && validationUseCase.checkIfPasswordValid(state.value.password)
        ) {
            setState {
                copy(
                    buttonEnabled = true
                )
            }
        } else {
            setState {
                copy(
                    buttonEnabled = false
                )
            }
        }
    }

    private fun signIn(haptic: HapticFeedback) {
        val loginBody = LoginRequestBody(state.value.login, state.value.password)
        setState { copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {

            loginUseCase.invoke(loginBody).collect { result ->
                result.onSuccess {
                    Log.e("a", it.token)
                    TokenManager(context).saveToken(it.token)
                    Network.token = it.token
                    setState { copy(isSuccess = true, isError = false) }
                    MainScope().launch {
                        setEffect { LoginContract.Effect.Navigation.ToMain }
                    }
                }.onFailure {
                    handleErrorUseCase.handleError(
                        error = it.message ?: "",
                        onTokenError = { },
                        onInputError = {
                            setState {
                                copy(
                                    isSuccess = false,
                                    errorMessage = "Неверный логин или пароль",
                                    isLoading = false
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