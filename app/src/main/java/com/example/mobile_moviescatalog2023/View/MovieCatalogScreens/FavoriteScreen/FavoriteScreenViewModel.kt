package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.MoviesListModel
import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import com.example.mobile_moviescatalog2023.domain.Entities.Models.ThreeFavoriteMovies
import com.example.mobile_moviescatalog2023.domain.UseCases.FavoriteMoviesUseCases.GetFavoriteMoviesUseCase
import com.example.mobile_moviescatalog2023.domain.UseCases.FromListToPartsMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
    private val getFavoriteMoviesUseCase :GetFavoriteMoviesUseCase,
    private val fromListToPartsMovieUseCase: FromListToPartsMovieUseCase
): BaseViewModel<FavoriteScreenContract.Event, FavoriteScreenContract.State, FavoriteScreenContract.Effect>() {

    init{ getFavoriteMovies() }
    override fun setInitialState() = FavoriteScreenContract.State(
        favoriteMovieList = null,
        isSuccess = null
    )

    override fun handleEvents(event: FavoriteScreenContract.Event) {
        when (event) {
            is FavoriteScreenContract.Event.GetFavoriteMovies -> getFavoriteMovies()
            is FavoriteScreenContract.Event.NavigationToMain -> setEffect { FavoriteScreenContract.Effect.Navigation.ToMain }
            is FavoriteScreenContract.Event.NavigationToProfile -> setEffect { FavoriteScreenContract.Effect.Navigation.ToProfile }
            is FavoriteScreenContract.Event.NavigationToFilm -> setEffect { FavoriteScreenContract.Effect.Navigation.ToFilm(id = event.id) }
        }
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteMoviesUseCase.invoke()
                .onSuccess {
                    setState { copy(
                        favoriteMovieList = fromListToPartsMovieUseCase.fromListToPartsMovies(it.movies),
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