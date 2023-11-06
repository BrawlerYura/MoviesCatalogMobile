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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
    private val getFavoriteMoviesUseCase :GetFavoriteMoviesUseCase
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
            getFavoriteMoviesUseCase.invoke().onSuccess {
                        it.movies?.forEach {
                            Log.e("a", it.name ?: "")
                        }
                        setState { copy(
                            favoriteMovieList = fromListToPartsMovies(it.movies),
                            isSuccess = true
                        ) }
                    }.onFailure {
                        setState { copy(
                            isSuccess = false
                        ) }
                    }
                }
    }

    private fun fromListToPartsMovies(movies: List<MovieElementModel>?): List<ThreeFavoriteMovies>? {
        val listFavoriteMovies: MutableList<ThreeFavoriteMovies> = mutableListOf()
        return if(movies != null) {
            var index = 0
            while(index < movies.count()) {
                val threeFavoriteMovies = when(movies.count() - index) {
                    2 -> {
                        ThreeFavoriteMovies(
                            firstMovie = movies[index],
                            secondMovie = movies[index + 1],
                            thirdMovie = null
                        )
                    }
                    1 -> {
                        ThreeFavoriteMovies(
                            firstMovie = null,
                            secondMovie = null,
                            thirdMovie = movies[index]
                        )
                    }
                    else -> {
                        ThreeFavoriteMovies(
                            firstMovie = movies[index],
                            secondMovie = movies[index + 1],
                            thirdMovie = movies[index + 2]
                        )
                    }
                }
                index += 3
                listFavoriteMovies += threeFavoriteMovies
            }
            listFavoriteMovies
        } else {
            null
        }
    }
}