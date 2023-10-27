package com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashScreenViewModel (
    private val context: Context
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    private val userRepository = UserRepository()
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
        val dataStore = TokenManager(context)

        viewModelScope.launch(Dispatchers.IO) {
            val tokenValue = dataStore.getToken.first()

            Network.token = tokenValue.toString()

            userRepository.getProfile()
                .collect { result ->
                    result.onSuccess {
                        setState { copy(isTryingGetToken = false, isSuccessGetToken = true) }
                    }.onFailure {
                        setState { copy(isTryingGetToken = false, isSuccessGetToken = false) }
                    }
                }
        }
    }
}