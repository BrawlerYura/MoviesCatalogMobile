package com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen

import android.content.Context
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.domain.Entities.RequestBodies.LoginRequestBody
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.AuthUseCases.LoginUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.ValidationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val context: Context,
    private val loginUseCase: LoginUseCase,
    private val validationUseCase: ValidationUseCase
): BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun setInitialState() = LoginContract.State(
        login = "",
        password = "",
        isSuccess = null,
        buttonEnabled = false,
        errorMessage = null
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

    private fun checkIfTextBoxesValid(){
        if(validationUseCase.checkIfLoginValid(state.value.login)
            && validationUseCase.checkIfPasswordValid(state.value.password)) {
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

        viewModelScope.launch(Dispatchers.IO) {
                loginUseCase.invoke(loginBody)
                .collect { result ->
                    result.onSuccess {
                        TokenManager(context).saveToken(it.token)
                        setState { copy(isSuccess = true) }
                        MainScope().launch {
                            setEffect { LoginContract.Effect.Navigation.ToMain }
                        }
                    }.onFailure {
                        setState { copy(isSuccess = false, errorMessage = "Неверный логин или пароль") }
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                }
        }
    }
}