package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FavoriteScreen

import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.Auth.AuthRepository
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MoviesListModel
import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.View.AuthScreens.LoginScreen.LoginContract
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
    val favoriteMoviesRepository: FavoriteMoviesRepository
): BaseViewModel<FavoriteScreenContract.Event, FavoriteScreenContract.State, FavoriteScreenContract.Effect>() {

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
    }

    private fun fromListToPartsMovies(movies: List<MovieElementModel>?): List<ThreeFavoriteMovies>? {
        var listFavoriteMovies: List<ThreeFavoriteMovies> = listOf()
        return if(movies != null) {
            var index = 0
            while(index < movies.count() - 3) {
                val threeFavoriteMovies = ThreeFavoriteMovies(
                    firstMovie = movies[index],
                    secondMovie = movies[index + 1],
                    thirdMovie = movies[index + 2]
                )
                index += 3
                listFavoriteMovies += threeFavoriteMovies
            }
            listFavoriteMovies
        } else {
            null
        }
    }
}

data class ThreeFavoriteMovies (
    val firstMovie: MovieElementModel?,
    val secondMovie: MovieElementModel?,
    val thirdMovie: MovieElementModel?
)