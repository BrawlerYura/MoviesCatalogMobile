package com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen

import android.content.Context
import android.util.Log
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.HandleErrorUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.CheckTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashScreenViewModel (
    private val checkTokenUseCase: CheckTokenUseCase,
    private val handleErrorUseCase: HandleErrorUseCase
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    init{ getToken() }

    override fun setInitialState() = SplashContract.State(
        isError = false,
        isNetworkError = false
    )

    override fun handleEvents(event: SplashContract.Event) {
        when (event) {
            is SplashContract.Event.GetToken -> getToken()
            is SplashContract.Event.OnTokenLoadedSuccess -> setEffect { SplashContract.Effect.Navigation.ToMain }
            is SplashContract.Event.OnTokenLoadedFailed -> setEffect { SplashContract.Effect.Navigation.ToIntroducingScreen }
        }
    }

    private fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            checkTokenUseCase.invoke().collect { result ->
                result.onSuccess {
                    setState { copy(isError = false, isNetworkError = false) }
                    Network.userId = it.id
                    MainScope().launch {
                        setEffect { SplashContract.Effect.Navigation.ToMain }
                    }
            }.onFailure {
                    handleErrorUseCase.handleError(
                        error = it.message ?: "",
                        onTokenError = {
                            setState { copy(isError = true, isNetworkError = false) }
                        },
                        onInputError = {

                        },
                        onOtherError = {
                            setState { copy(isNetworkError = true) }
                        }
                    )
                }
            }
        }
    }
}