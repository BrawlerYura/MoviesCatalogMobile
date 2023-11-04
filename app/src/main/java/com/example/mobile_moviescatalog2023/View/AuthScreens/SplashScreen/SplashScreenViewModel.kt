package com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.UseCases.UserUseCases.GetProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashScreenViewModel (
    private val getProfileUseCase: GetProfileUseCase
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override fun setInitialState() = SplashContract.State(
        isTryingGetToken = true,
        isSuccessGetToken = false
    )

    override fun handleEvents(event: SplashContract.Event) {
        when (event) {
            is SplashContract.Event.GetToken -> getToken()
        }
    }

    private fun getToken() {

        viewModelScope.launch(Dispatchers.IO) {
            getProfileUseCase.invoke()
                .collect { result ->
                    result.onSuccess {
                        Network.userId = it.id
                        setState { copy(isTryingGetToken = false, isSuccessGetToken = true) }
                    }.onFailure {
                        setState { copy(isTryingGetToken = false, isSuccessGetToken = false) }
                    }
                }
        }
    }
}