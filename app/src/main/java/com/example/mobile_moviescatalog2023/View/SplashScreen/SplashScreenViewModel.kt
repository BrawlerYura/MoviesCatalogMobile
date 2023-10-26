package com.example.mobile_moviescatalog2023.View.SplashScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Network
import com.example.mobile_moviescatalog2023.Network.User.UserRepository
import com.example.mobile_moviescatalog2023.TokenManager.TokenManager
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel (
    private val context: Context
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    private val userRepository = UserRepository()
    override fun setInitialState() =  SplashContract.State (
        isTryingGetToken = true,
        isSuccessGetToken = false
    )

    override fun handleEvents(event: SplashContract.Event) {
        when (event) {
            is SplashContract.Event.GetToken -> getToken()
        }
    }

    private fun getToken() {
        Log.e("a", "1")
        viewModelScope.launch(Dispatchers.IO) {
            val dataStore = TokenManager(context)
            dataStore.getToken().collect { value ->
                Network.token = value.toString()
                Log.e("a", value.toString())
            }
        }

        Log.e("a", "2")
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("a", "3")
            userRepository.getProfile()
                    .collect { result ->
                    result.onSuccess {
                        Log.e("a", "collected")
                        setState { copy(isTryingGetToken = false, isSuccessGetToken = true) }
                    }.onFailure {
                        Log.e("a", "collect")
                        setState { copy(isTryingGetToken = false, isSuccessGetToken = false) }
                    }
                }
        }
    }
}