package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.ProfileScreen

import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel

class ProfileScreenViewModel(
): BaseViewModel<ProfileScreenContract.Event, ProfileScreenContract.State, ProfileScreenContract.Effect>() {

    override fun setInitialState() = ProfileScreenContract.State(
        temp = ""
    )

    override fun handleEvents(event: ProfileScreenContract.Event) {
//        when (event) {
//            is MainScreenContract.Event
//        }
    }
}