package com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.CheckTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenViewModel (
    private val checkTokenUseCase: CheckTokenUseCase
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
        Log.e("dadasd", "sdasd")
        viewModelScope.launch(Dispatchers.IO) {
            checkTokenUseCase.invoke()
            .onSuccess {
                    Network.userId = it.id
                    setState { copy(isSuccessGetToken = true) }
                }.onFailure {
                    Log.e("dadasd", "sdasd")
                    setState { copy(isError = true) }
                }

        }
    }
}