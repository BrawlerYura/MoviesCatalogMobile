package com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen

import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.GetProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenViewModel (
    private val getProfileUseCase: GetProfileUseCase
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    init{ getToken() }

    override fun setInitialState() = SplashContract.State(
        isSuccessGetToken = false,
        isError = false
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
            getProfileUseCase.invoke()
                .collect { result ->
                    result.onSuccess {
                        Network.userId = it.id
                        setState { copy(isSuccessGetToken = true) }

                    }.onFailure {
                        setState { copy(isError = true) }
                    }
                }
        }
    }
}