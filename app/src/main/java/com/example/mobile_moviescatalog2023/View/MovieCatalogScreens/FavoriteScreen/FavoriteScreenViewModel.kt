package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen

import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel

class FavoriteScreenViewModel(
): BaseViewModel<FavoriteScreenContract.Event, FavoriteScreenContract.State, FavoriteScreenContract.Effect>() {

    override fun setInitialState() = FavoriteScreenContract.State(
        temp = ""
    )

    override fun handleEvents(event: FavoriteScreenContract.Event) {
//        when (event) {
//            is MainScreenContract.Event
//        }
    }
}