package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen

import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
): BaseViewModel<FavoriteScreenContract.Event, FavoriteScreenContract.State, FavoriteScreenContract.Effect>() {

    val favoriteMoviesRepository = FavoriteMoviesRepository()
    override fun setInitialState() = FavoriteScreenContract.State(
        favoriteMovieList = null,
        isSuccess = null
    )

    override fun handleEvents(event: FavoriteScreenContract.Event) {
        when (event) {
            is FavoriteScreenContract.Event.GetFavoriteMovies -> getFavoriteMovies()
        }
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMoviesRepository.getFavoriteMovies()
                .collect { result ->
                    result.onSuccess {
                        setState { copy(
                            favoriteMovieList = it.movies,
                            isSuccess = true
                        ) }
                    }.onFailure {
                        setState { copy(
                            isSuccess = false
                        ) }
                    }
                }
        }
    }
}