package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.MainScreen

import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel

class MainScreenViewModel(
): BaseViewModel<MainScreenContract.Event, MainScreenContract.State, MainScreenContract.Effect>() {

    override fun setInitialState() = MainScreenContract.State(
        temp = ""
    )

    override fun handleEvents(event: MainScreenContract.Event) {
//        when (event) {
//            is MainScreenContract.Event
//        }
    }
}