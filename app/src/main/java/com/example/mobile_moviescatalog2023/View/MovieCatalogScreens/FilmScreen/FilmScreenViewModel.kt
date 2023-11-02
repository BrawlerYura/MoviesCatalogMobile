package com.example.mobile_moviescatalog2023.View.MovieCatalogScreens.FilmScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mobile_moviescatalog2023.Network.DataClasses.Models.MovieElementModel
import com.example.mobile_moviescatalog2023.Network.FavoriteMovies.FavoriteMoviesRepository
import com.example.mobile_moviescatalog2023.Network.Movie.MovieRepository
import com.example.mobile_moviescatalog2023.View.Base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class FilmScreenViewModel(
    val movieRepository: MovieRepository,
    val favoriteMoviesRepository: FavoriteMoviesRepository
): BaseViewModel<FilmScreenContract.Event, FilmScreenContract.State, FilmScreenContract.Effect>() {

    override fun setInitialState() = FilmScreenContract.State(
        isSuccess = null,
        movieDetails = null,
        isAddingSuccess = null,
        isDeletingSuccess = null,
        isMyFavorite = false
    )

    override fun handleEvents(event: FilmScreenContract.Event) {
        when(event) {
            is FilmScreenContract.Event.LoadFilmDetails -> loadFilmDetails(id = event.id)
            is FilmScreenContract.Event.AddToFavorite -> addToFavorite(id = event.id)
            is FilmScreenContract.Event.DeleteFavorite -> deleteFavorite(id = event.id)
        }
    }

    private fun loadFilmDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovieDetails(id)
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                isSuccess = true,
                                movieDetails = it
                            )
                        }
                        checkIfFavorite(id = it.id)
                    }.onFailure {
                        setState {
                            copy(
                                isSuccess = false
                            )
                        }
                    }
                }
        }
    }

    private fun addToFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMoviesRepository.addFavoriteMovie(id)
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                isAddingSuccess = true,
                                isMyFavorite = true
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                isAddingSuccess = false
                            )
                        }
                    }
                }
        }
    }

    private fun deleteFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMoviesRepository.deleteFavoriteMovie(id)
                .collect { result ->
                    result.onSuccess {
                        setState {
                            copy(
                                isDeletingSuccess = true,
                                isMyFavorite = false
                            )
                        }
                    }.onFailure {
                        setState {
                            copy(
                                isDeletingSuccess = false
                            )
                        }
                    }
                }
        }
    }

    private fun checkIfFavorite(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMoviesRepository.getFavoriteMovies()
                .collect { result ->
                    result.onSuccess {
                        if(it.movies != null) {
                            it.movies.forEach {
                                if(id == it.id) {
                                    setState {
                                        copy(
                                            isMyFavorite = true
                                        )
                                    }
                                }
                            }
                        } else {
                            setState {
                                copy(
                                    isMyFavorite = false
                                )
                            }
                        }
                    }.onFailure {

                    }
                }
        }
    }
}